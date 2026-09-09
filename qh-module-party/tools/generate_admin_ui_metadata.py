#!/usr/bin/env python3
"""
Regenerates the "party" bounded-context entries in qh-admin-ui's
entitiesMeta.json by parsing the actual Party entity/controller Java sources
directly - so the admin UI's metadata can never drift from the real schema.

The original entitiesMeta.json generator used to build this repo's UI
metadata was never committed (see qh-admin-ui/README.md's note on how the
file was produced); this script is a from-scratch replacement, scoped to the
"party" bounded context only. It leaves every other bounded context's entries
(currently: productbuilder) untouched.

Usage: python3 generate_admin_ui_metadata.py
Reads:  qh-module-party/{reference,core}/src/main/java/ir/bank/qh/party/{reference,core}/{entity,controller}/*.java
Writes: qh-admin-ui/src/data/entitiesMeta.json (party entries replaced)
"""
import json
import re
import os

REPO_ROOT = os.path.normpath(os.path.join(os.path.dirname(__file__), '..', '..'))
MODULES = ['reference', 'core']
ENTITY_DIRS = [
    os.path.join(REPO_ROOT, f'qh-module-party/{m}/src/main/java/ir/bank/qh/party/{m}/entity')
    for m in MODULES
]
CONTROLLER_DIRS = [
    os.path.join(REPO_ROOT, f'qh-module-party/{m}/src/main/java/ir/bank/qh/party/{m}/controller')
    for m in MODULES
]
META_PATH = os.path.join(REPO_ROOT, 'qh-admin-ui/src/data/entitiesMeta.json')

BASE_ENTITY_FIELDS = [
    {'field': 'createdBy', 'column': 'CREATED_BY', 'javaType': 'String', 'nullable': True, 'length': 100},
    {'field': 'createdOn', 'column': 'CREATED_ON', 'javaType': 'Date', 'nullable': True, 'length': None},
    {'field': 'modifiedBy', 'column': 'MODIFIED_BY', 'javaType': 'String', 'nullable': True, 'length': 100},
    {'field': 'modifiedOn', 'column': 'MODIFIED_ON', 'javaType': 'Date', 'nullable': True, 'length': None},
    {'field': 'version', 'column': 'RECORD_VERSION', 'javaType': 'Long', 'nullable': True, 'length': None},
]
TENANT_FIELD = {'field': 'institutionId', 'column': 'INSTITUTION_ID', 'javaType': 'Long', 'nullable': True, 'length': None}


def widget_for(java_type, is_fk, is_bool):
    if is_fk:
        return 'fk'
    if is_bool:
        return 'checkbox'
    if java_type in ('Long', 'Integer', 'BigDecimal', 'int'):
        return 'number'
    if java_type == 'Date':
        return 'date'
    return 'text'


def strip_line_comments(src):
    # Remove // line comments (naively - no // occurs inside our string literals here)
    return re.sub(r'//.*', '', src)


def parse_entity(path):
    with open(path, encoding='utf-8') as f:
        src = f.read()

    class_name = os.path.splitext(os.path.basename(path))[0]

    table_m = re.search(r'@Table\(\s*schema\s*=\s*"([^"]+)"\s*,\s*name\s*=\s*"([^"]+)"', src)
    schema, table = table_m.group(1), table_m.group(2)

    extends_m = re.search(r'class\s+\w+\s+extends\s+(\w+)', src)
    base = extends_m.group(1) if extends_m else None
    is_tenant_aware = base == 'TenantAwareEntity'
    is_base_entity = base in ('BaseEntity', 'TenantAwareEntity')

    fields = []
    if is_base_entity:
        fields.extend(BASE_ENTITY_FIELDS)
        if is_tenant_aware:
            fields.append(TENANT_FIELD)

    # Split the class body into per-field chunks: each chunk is exactly the
    # source text between the end of the previous field declaration (or the
    # start of the class body, or the end of a preceding method) and the
    # start of this one - i.e. this field's own annotations.
    body_m = re.search(r'\{(.*)\}\s*$', src, re.S)
    body = body_m.group(1)
    field_matches = list(re.finditer(r'private\s+([\w<>\[\]]+)\s+(\w+)\s*(?:=\s*[^;]+)?;', body))
    chunk_start = 0
    for fld_m in field_matches:
        java_type_raw, field_name = fld_m.group(1), fld_m.group(2)
        ann_text = body[chunk_start:fld_m.start()]
        chunk_start = fld_m.end()
        # A field-looking match inside a method body (there are none in these
        # entities) would pull in unrelated preceding text; guard against it
        # by only trusting an annotation zone that contains no semicolons of
        # its own statements (i.e. is purely annotations/whitespace/blank).
        if re.search(r';', re.sub(r'@\w+\([^)]*\)', '', ann_text)):
            continue

        is_id = '@Id' in ann_text
        is_fk = ('@ManyToOne' in ann_text or '@OneToOne' in ann_text) and '@JoinColumn' in ann_text
        is_bool = 'YesNoConverter' in ann_text or java_type_raw == 'Boolean'

        col_m = re.search(r'@(?:Join)?Column\(\s*name\s*=\s*"([^"]+)"', ann_text)
        if not col_m:
            continue  # not a persisted column field (e.g. a plain @Transient or collection - shouldn't happen here)
        column = col_m.group(1)

        length_m = re.search(r'length\s*=\s*(\d+)', ann_text)
        length = int(length_m.group(1)) if length_m else None

        if is_fk:
            nullable = 'optional = false' not in ann_text and 'nullable = false' not in ann_text
            java_type = java_type_raw  # target entity class name
        else:
            nullable_m = re.search(r'nullable\s*=\s*(true|false)', ann_text)
            nullable = (nullable_m.group(1) == 'true') if nullable_m else True
            java_type = java_type_raw

        fields.append({
            'field': field_name,
            'column': column,
            'javaType': java_type,
            'nullable': nullable,
            'length': length,
            'isId': is_id,
            'isFk': is_fk,
            'isBool': is_bool,
        })

    return {
        'entity': class_name,
        'schema': schema,
        'table': table,
    }, fields


def parse_controller(entity_name):
    ctrl_path = None
    for d in CONTROLLER_DIRS:
        candidate = os.path.join(d, entity_name + 'Controller.java')
        if os.path.exists(candidate):
            ctrl_path = candidate
            break
    if ctrl_path is None:
        return None, True
    with open(ctrl_path, encoding='utf-8') as f:
        src = f.read()
    path_m = re.search(r'@RequestMapping\("([^"]+)"\)', src)
    path = path_m.group(1) if path_m else None
    readonly = '@PostMapping' not in src
    return path, readonly


def to_title(entity_name):
    name = entity_name[:-len('Entity')] if entity_name.endswith('Entity') else entity_name
    # split PascalCase into words
    words = re.findall(r'[A-Z][a-z0-9]*', name)
    return ' '.join(words) if words else name


def to_slug(entity_name):
    name = entity_name[:-len('Entity')] if entity_name.endswith('Entity') else entity_name
    words = re.findall(r'[A-Z][a-z0-9]*', name)
    return '-'.join(w.lower() for w in words)


PREFERRED_LABEL_FIELDS = [
    'titleFa', 'fullName', 'registeredName', 'groupName', 'customerNo',
    'contactValue', 'identifierValue', 'addressLine1', 'fieldName',
]


def pick_label_field(field_names):
    for f in PREFERRED_LABEL_FIELDS:
        if f in field_names:
            return f
    return 'createdBy'


def main():
    with open(META_PATH, encoding='utf-8') as f:
        data = json.load(f)
    non_party = [e for e in data if e.get('boundedContext') != 'party']

    entity_paths = sorted(
        os.path.join(d, fn)
        for d in ENTITY_DIRS
        for fn in os.listdir(d) if fn.endswith('.java')
    )
    out_entities = []
    for entity_path in entity_paths:
        meta, raw_fields = parse_entity(entity_path)
        entity_name = meta['entity']
        path, readonly = parse_controller(entity_name)
        if path is None:
            print(f'WARN: no controller found for {entity_name}, skipping')
            continue

        fields_out = []
        for rf in raw_fields:
            is_fk = rf.get('isFk', False)
            is_bool = rf.get('isBool', False)
            widget = widget_for(rf['javaType'], is_fk, is_bool)
            fields_out.append({
                'field': rf['field'],
                'column': rf['column'],
                'javaType': rf['javaType'],
                'nullable': rf['nullable'],
                'length': rf['length'],
                'unique': False,
                'isId': rf.get('isId', False),
                'widget': widget,
                'fkTarget': rf['javaType'] if is_fk else None,
                'enumValues': None,
                'default': None,
            })

        field_names = {f['field'] for f in fields_out}
        label_field = pick_label_field(field_names)

        out_entities.append({
            'path': path,
            'entity': entity_name,
            'boundedContext': 'party',
            'domain': 'party',
            'readonly': readonly,
            'schema': meta['schema'],
            'table': meta['table'],
            'fields': fields_out,
            'labelField': label_field,
            'title': to_title(entity_name),
            'slug': to_slug(entity_name),
            'domainTitle': 'اشخاص (Party)',
            'boundedContextTitle': 'اشخاص (Party)',
        })

    merged = non_party + out_entities
    with open(META_PATH, 'w', encoding='utf-8') as f:
        json.dump(merged, f, ensure_ascii=False, indent=2)
        f.write('\n')

    print(f'Wrote {len(out_entities)} party entities ({sum(len(e["fields"]) for e in out_entities)} fields total)')
    print(f'Non-party entities preserved: {len(non_party)}')


if __name__ == '__main__':
    main()
