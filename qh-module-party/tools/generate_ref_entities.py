import json, re, os

TOOLS_DIR = os.path.dirname(os.path.abspath(__file__))
SCHEMA_PATH = os.path.join(TOOLS_DIR, 'party-ea-schema.json')
OUT_ROOT = os.path.normpath(os.path.join(TOOLS_DIR, '..', 'src/main/java/ir/bank/qh/party'))

schema = json.load(open(SCHEMA_PATH, encoding='utf-8'))
tables = schema['tables']

REF_PARENT_FK = {
    'REF_PROVINCE': [('COUNTRY_CODE', 'REF_COUNTRY', 'country')],
    'REF_COUNTY': [('PROVINCE_CODE', 'REF_PROVINCE', 'province')],
    'REF_CITY': [('PROVINCE_CODE', 'REF_PROVINCE', 'province'), ('COUNTY_CODE', 'REF_COUNTY', 'county')],
    'REF_DISTRICT': [('CITY_CODE', 'REF_CITY', 'city')],
    'REF_AUDIT_ENTITY_TYPE': [('DEFAULT_SCOPE_CODE', 'REF_AUDIT_SCOPE', 'defaultScope')],
}

def pascal(name):
    return ''.join(w.capitalize() for w in name.split('_') if w)

def camel(name):
    p = pascal(name)
    return p[0].lower() + p[1:] if p else p

def kebab(name):
    return name.lower().replace('_', '-')

def pluralize_kebab(k):
    if k.endswith(('s', 'x', 'z', 'ch', 'sh')):
        return k + 'es'
    if len(k) >= 2 and k[-1] == 'y' and k[-2] not in 'aeiou':
        return k[:-1] + 'ies'
    return k + 's'

def entity_class_name(table):
    if table == 'GEOGRAPHIC_LOCATION':
        return 'GeographicLocationEntity'
    return pascal(table) + 'Entity'

def url_path(table):
    base = 'geographic-locations' if table == 'GEOGRAPHIC_LOCATION' else pluralize_kebab(kebab(table))
    return '/api/v1/party/' + base

ref_tables = sorted([t for t in tables if t.startswith('REF_')]) + ['GEOGRAPHIC_LOCATION']
print(f'{len(ref_tables)} reference-shaped tables to generate')

os.makedirs(f'{OUT_ROOT}/entity', exist_ok=True)
os.makedirs(f'{OUT_ROOT}/repository', exist_ok=True)
os.makedirs(f'{OUT_ROOT}/service', exist_ok=True)
os.makedirs(f'{OUT_ROOT}/controller', exist_ok=True)

def clean_doc(doc):
    doc = (doc or '').strip()
    doc = doc.replace('*/', '* /')
    return doc

def write(path, content):
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)

generated = []

for table in ref_tables:
    t = tables[table]
    cols = t['columns']
    pk_col = cols[0]
    entity_name = entity_class_name(table)
    is_geo = (table == 'GEOGRAPHIC_LOCATION')
    id_java_type = 'Long' if is_geo else 'String'
    doc = clean_doc(t['doc'])

    parent_fks = REF_PARENT_FK.get(table, [])
    parent_col_names = {p[0] for p in parent_fks}

    # ---- Entity ----
    lines = []
    lines.append('package ir.bank.qh.party.entity;')
    lines.append('')
    lines.append('import ir.bank.qh.common.entity.BaseEntity;')
    lines.append('import ir.bank.qh.party.converter.YesNoConverter;')
    lines.append('import jakarta.persistence.*;')
    lines.append('import lombok.EqualsAndHashCode;')
    lines.append('import lombok.Getter;')
    lines.append('import lombok.Setter;')
    lines.append('')
    lines.append('/**')
    if doc:
        lines.append(f' * {doc}')
    lines.append(f' * Maps to table {table}.')
    lines.append(' */')
    lines.append('@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)')
    lines.append('@Entity')
    lines.append(f'@Table(schema = "PARTY", name = "{table}")')
    if is_geo:
        lines.append(f'@SequenceGenerator(name = "{table}_ID_SEQ", schema = "PARTY", sequenceName = "{table}_ID_SEQ", allocationSize = 1)')
    lines.append('@Getter')
    lines.append('@Setter')
    lines.append(f'public class {entity_name} extends BaseEntity {{')
    lines.append('')
    if is_geo:
        lines.append('    @Id')
        lines.append(f'    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "{table}_ID_SEQ")')
        lines.append(f'    @Column(name = "{pk_col["name"]}")')
        lines.append('    @EqualsAndHashCode.Include')
        lines.append('    private Long id;')
    else:
        length = pk_col['length'] or '50'
        lines.append('    // Named "id" (not "code") so JpaReferenceResolver - which looks up a field literally')
        lines.append('    // named "id" to resolve a client-supplied {"id": ...} reference - works unmodified for')
        lines.append('    // this natural-key (String) PK the same way it already does for every Long-id entity.')
        lines.append('    @Id')
        lines.append(f'    @Column(name = "{pk_col["name"]}", length = {length})')
        lines.append('    @EqualsAndHashCode.Include')
        lines.append('    private String id;')
    lines.append('')

    for c in cols[1:]:
        cname = c['name']
        if cname in parent_col_names:
            continue
        jname = camel(cname)
        length = c['length']
        nullable = c['nullable']
        if cname == 'IS_ACTIVE':
            lines.append('    @Convert(converter = YesNoConverter.class)')
            nn = 'true' if nullable else 'false'
            lines.append(f'    @Column(name = "IS_ACTIVE", nullable = {nn}, length = 1, columnDefinition = "char(1) default \'Y\'")')
            lines.append('    private Boolean active = Boolean.TRUE;')
        elif cname == 'DISPLAY_ORDER':
            nn = 'true' if nullable else 'false'
            lines.append(f'    @Column(name = "DISPLAY_ORDER", nullable = {nn}, columnDefinition = "number default 0")')
            lines.append('    private Integer displayOrder = 0;')
        elif c['type'] in ('NVARCHAR2', 'VARCHAR2', 'CHAR'):
            nn = 'false' if not nullable else 'true'
            len_part = f', length = {length}' if length and length != '0' else ''
            nn_part = '' if nullable else ', nullable = false'
            lines.append(f'    @Column(name = "{cname}"{nn_part}{len_part})')
            lines.append(f'    private String {jname};')
        else:
            nn_part = '' if nullable else ', nullable = false'
            lines.append(f'    @Column(name = "{cname}"{nn_part})')
            lines.append(f'    private String {jname}; // type={c["type"]}')
        lines.append('')

    col_by_name = {c['name']: c for c in cols}
    for col_name, target_table, field_name in parent_fks:
        target_entity = entity_class_name(target_table)
        target_pk = tables[target_table]['columns'][0]['name']
        fk_name = f'{table[:20]}_FK_{col_name.replace("_CODE","")}'[:30]
        col_nullable = col_by_name[col_name]['nullable']
        if col_nullable:
            lines.append('    @ManyToOne(fetch = FetchType.LAZY)')
            lines.append(f'    @JoinColumn(name = "{col_name}", referencedColumnName = "{target_pk}",')
        else:
            lines.append('    @ManyToOne(fetch = FetchType.LAZY, optional = false)')
            lines.append(f'    @JoinColumn(name = "{col_name}", referencedColumnName = "{target_pk}", nullable = false,')
        lines.append(f'            foreignKey = @ForeignKey(name = "{fk_name}"))')
        lines.append(f'    private {target_entity} {field_name};')
        lines.append('')

    lines.append('}')
    write(f'{OUT_ROOT}/entity/{entity_name}.java', '\n'.join(lines) + '\n')

    # ---- Repository ----
    repo_name = entity_name + 'Repository'
    repo = f'''package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.{entity_name};
import org.springframework.data.jpa.repository.JpaRepository;

public interface {repo_name} extends JpaRepository<{entity_name}, {id_java_type}> {{
}}
'''
    write(f'{OUT_ROOT}/repository/{repo_name}.java', repo)

    # ---- Service ----
    svc_name = entity_name + 'Service'
    svc = f'''package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.{entity_name};
import ir.bank.qh.party.repository.{repo_name};
import ir.bank.qh.common.exception.ResourceNotFoundException;
import ir.bank.qh.common.service.JpaReferenceResolver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for {entity_name}. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class {svc_name} {{

    private final {repo_name} repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<{entity_name}> findAll() {{
        return repository.findAll();
    }}

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public {entity_name} findById({id_java_type} id) {{
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("{entity_name}", id));
    }}

    public {entity_name} create({entity_name} entity) {{
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }}

    public {entity_name} update({id_java_type} id, {entity_name} incoming) {{
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        {entity_name} existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties({entity_name}.class));
        return repository.save(existing);
    }}

    public void delete({id_java_type} id) {{
        {entity_name} existing = findById(id);
        repository.delete(existing);
    }}
}}
'''
    write(f'{OUT_ROOT}/service/{svc_name}.java', svc)

    # ---- Controller ----
    ctrl_name = entity_name + 'Controller'
    path = url_path(table)
    ctrl = f'''package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.{entity_name};
import ir.bank.qh.party.service.{svc_name};
import ir.bank.qh.common.controller.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoints for {entity_name}. Base path: {path}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("{path}")
public class {ctrl_name} {{

    private final {svc_name} service;

    @GetMapping
    public ApiResponse<List<{entity_name}>> findAll() {{
        return ApiResponse.ok(service.findAll());
    }}

    @GetMapping("/{{id}}")
    public ApiResponse<{entity_name}> findById(@PathVariable {id_java_type} id) {{
        return ApiResponse.ok(service.findById(id));
    }}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<{entity_name}> create(@Valid @RequestBody {entity_name} entity) {{
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }}

    @PutMapping("/{{id}}")
    public ApiResponse<{entity_name}> update(@PathVariable {id_java_type} id, @Valid @RequestBody {entity_name} entity) {{
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }}

    @DeleteMapping("/{{id}}")
    public ApiResponse<Void> delete(@PathVariable {id_java_type} id) {{
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }}
}}
'''
    write(f'{OUT_ROOT}/controller/{ctrl_name}.java', ctrl)

    generated.append({
        'table': table,
        'entity': entity_name,
        'idType': id_java_type,
        'path': path,
        'pkColumn': pk_col['name'],
    })

print('Done. Generated', len(generated), 'entities x 4 files =', len(generated)*4, 'files')
