// Generates a plausible example value for a single field, purely from its
// metadata (widget/type/column-name hints/length) - no backend knowledge
// required. Used by the "پر کردن با مقادیر نمونه" button on every form.
import { isAuditField } from './meta';

function truncate(s, length) {
  if (!length) return s;
  return s.length > length ? s.slice(0, length) : s;
}

function numberForColumn(field) {
  const col = field.column || field.field.toUpperCase();
  if (/MIN_AGE/.test(col)) return 18;
  if (/MAX_AGE/.test(col)) return 65;
  if (/PERCENT/.test(col)) return 10;
  if (/RATE/.test(col)) return 12.5;
  if (/(AMOUNT|BALANCE)/.test(col)) return 1000000;
  if (/COUNT/.test(col)) return 1;
  if (/(VERSION_NO|PRIORITY_NO|_NO)$/.test(col)) return 1;
  if (/PERIOD|DAYS|MONTHS|TERM/.test(col)) return 12;
  return 1;
}

// Short, changes-every-call suffix so repeated "fill with example" clicks or
// scenario re-runs never collide with a UNIQUE column from a previous run.
function uniqueSuffix() {
  return Date.now().toString(36).slice(-5).toUpperCase();
}

// Appends the suffix without exceeding the column's length limit, trimming
// the base text (never the suffix) when the two together would overflow. For
// a column too short to fit even the suffix itself (e.g. a 3-char ISO country
// code), falls back to just the tail of the raw unique token, still capped to
// `length` - a truncated-but-still-attached suffix would otherwise silently
// exceed the column and fail on insert.
function withUniqueSuffix(base, length) {
  const suffix = '_' + uniqueSuffix();
  if (!length) return base + suffix;
  if (suffix.length >= length) {
    return uniqueSuffix().slice(-length);
  }
  if (base.length + suffix.length > length) {
    return base.slice(0, length - suffix.length) + suffix;
  }
  return base + suffix;
}

function sampleForColumn(field) {
  const col = field.column || field.field.toUpperCase();
  const name = field.field;

  if (/CURRENCY/.test(col)) return 'IRR';
  if (/CODE$/.test(col)) {
    const tag = name.replace(/Code$/, '').replace(/([a-z])([A-Z])/g, '$1_$2').toUpperCase();
    return withUniqueSuffix(`SAMPLE_${tag || 'CODE'}`, field.length);
  }
  if (/(NAME)$/.test(col)) return withUniqueSuffix('نمونه ' + name.replace(/Name$/, ''), field.length);
  if (/DESCRIPTION/.test(col)) return truncate('توضیحات نمونه برای تست این سناریو', field.length);
  if (/REFERENCE/.test(col)) return withUniqueSuffix('REF', field.length);
  return withUniqueSuffix(`نمونه-${name}`, field.length);
}

export function buildExampleValue(field) {
  switch (field.widget) {
    case 'checkbox':
      return field.default === 'false' ? false : true;
    case 'number':
      return numberForColumn(field);
    case 'date':
      return new Date().toISOString().slice(0, 10);
    case 'datetime-local':
      return new Date().toISOString().slice(0, 16);
    case 'select':
      return field.enumValues?.[0] ?? '';
    case 'fk':
      return null; // resolved separately via the FK picker's loaded options
    case 'text':
    default:
      return sampleForColumn(field);
  }
}

// Builds a full example payload for an entity's editable fields (skips id +
// audit fields; FK fields are left null for the caller to fill in once the
// related record list has loaded).
export function buildExamplePayload(entity, skipFieldNames = new Set()) {
  const payload = {};
  for (const f of entity.fields) {
    if (f.isId || isAuditField(f.field) || skipFieldNames.has(f.field)) continue;
    payload[f.field] = buildExampleValue(f);
  }
  return payload;
}
