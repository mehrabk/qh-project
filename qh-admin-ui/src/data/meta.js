import raw from './entitiesMeta.json';

// raw: array of 64 entries, one per REST resource, each shaped like:
// {
//   path, entity, boundedContext, domain, domainTitle, boundedContextTitle,
//   readonly, schema, table, title, slug, labelField,
//   fields: [{ field, column, javaType, nullable, length, unique, isId,
//              widget, fkTarget, enumValues, default }]
// }

export const entities = raw;

export const bySlug = Object.fromEntries(raw.map((e) => [e.slug, e]));

export const byEntityName = Object.fromEntries(raw.map((e) => [e.entity, e]));

// bounded context -> domain -> [entities], in the same dependency order the
// backend README documents (reference/core/commonrules/deposit/loan, party).
const DOMAIN_ORDER = ['reference', 'core', 'commonrules', 'deposit', 'loan', 'party'];

export const navigation = (() => {
  const groups = {};
  for (const e of raw) {
    groups[e.boundedContext] ??= { title: e.boundedContextTitle, domains: {} };
    groups[e.boundedContext].domains[e.domain] ??= { title: e.domainTitle, entities: [] };
    groups[e.boundedContext].domains[e.domain].entities.push(e);
  }
  for (const bc of Object.values(groups)) {
    bc.domains = Object.fromEntries(
      Object.entries(bc.domains).sort(
        (a, b) => DOMAIN_ORDER.indexOf(a[0]) - DOMAIN_ORDER.indexOf(b[0])
      )
    );
    for (const d of Object.values(bc.domains)) {
      d.entities.sort((a, b) => a.title.localeCompare(b.title));
    }
  }
  return groups;
})();

export function displayValue(entity, item) {
  const f = entity.labelField;
  if (item == null) return '';
  const v = item[f];
  if (v !== undefined && v !== null && v !== '') return String(v);
  return item.id != null ? `#${item.id}` : '';
}

// Non-audit fields shown in the table/form by default (audit/version/tenant
// columns exist on every entity via BaseEntity/TenantAwareEntity but clutter
// the demo UI - they're still visible via the "نمایش فیلدهای Audit" toggle).
const AUDIT_FIELDS = new Set([
  'createdBy', 'createdOn', 'modifiedBy', 'modifiedOn', 'version', 'institutionId',
]);
export function isAuditField(fieldName) {
  return AUDIT_FIELDS.has(fieldName);
}
