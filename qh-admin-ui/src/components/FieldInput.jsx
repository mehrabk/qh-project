import { displayValue } from '../data/meta';
import { useFkOptions } from '../hooks/useFkOptions';
import { FIELD_LABELS } from '../data/fieldLabels';
import { enumValueLabel } from '../data/enumLabels';

function humanize(fieldName) {
  const withSpaces = fieldName.replace(/([a-z0-9])([A-Z])/g, '$1 $2');
  return withSpaces.charAt(0).toUpperCase() + withSpaces.slice(1);
}

export function fieldLabel(field) {
  return FIELD_LABELS[field.field] || humanize(field.field);
}

function FkSelect({ field, value, onChange }) {
  const { loading, error, items, targetEntity } = useFkOptions(field.fkTarget);
  // The target's own PK can be a Long surrogate id (most entities) or a String
  // natural key (every REF_* catalog, e.g. GENDER_CODE 'MALE') - only cast to
  // Number for the former, or a string id like 'VERIFIED' becomes NaN and the
  // FK silently resolves to null.
  const idField = targetEntity?.fields.find((f) => f.isId);
  const idIsNumeric = idField ? idField.javaType !== 'String' : true;
  return (
    <div className="fk-field">
      <select
        value={value ?? ''}
        onChange={(e) => onChange(e.target.value ? (idIsNumeric ? Number(e.target.value) : e.target.value) : null)}
        required={!field.nullable}
      >
        <option value="">{loading ? 'در حال بارگذاری...' : '— انتخاب کنید —'}</option>
        {items.map((item) => (
          <option key={item.id} value={item.id}>
            {typeof item.id === 'number' ? `#${item.id}` : item.id} — {targetEntity ? displayValue(targetEntity, item) : ''}
          </option>
        ))}
      </select>
      {error && <span className="field-error">خطا در بارگذاری {field.fkTarget}: {error}</span>}
      {!loading && items.length === 0 && !error && (
        <span className="field-hint">هنوز رکوردی برای {field.fkTarget} ثبت نشده — ابتدا آن را بسازید.</span>
      )}
    </div>
  );
}

export default function FieldInput({ field, value, onChange }) {
  if (field.widget === 'fk') {
    return <FkSelect field={field} value={value} onChange={onChange} />;
  }
  if (field.widget === 'select') {
    return (
      <select value={value ?? ''} onChange={(e) => onChange(e.target.value || null)} required={!field.nullable}>
        <option value="">— انتخاب کنید —</option>
        {(field.enumValues || []).map((v) => (
          <option key={v} value={v}>{enumValueLabel(v)}</option>
        ))}
      </select>
    );
  }
  if (field.widget === 'checkbox') {
    return (
      <input
        type="checkbox"
        checked={!!value}
        onChange={(e) => onChange(e.target.checked)}
      />
    );
  }
  if (field.widget === 'number') {
    return (
      <input
        type="number"
        step="any"
        value={value ?? ''}
        required={!field.nullable}
        onChange={(e) => onChange(e.target.value === '' ? null : Number(e.target.value))}
      />
    );
  }
  if (field.widget === 'date') {
    return (
      <input
        type="date"
        value={value ? String(value).slice(0, 10) : ''}
        required={!field.nullable}
        onChange={(e) => onChange(e.target.value || null)}
      />
    );
  }
  if (field.widget === 'datetime-local') {
    return (
      <input
        type="datetime-local"
        value={value ? String(value).slice(0, 16) : ''}
        required={!field.nullable}
        onChange={(e) => onChange(e.target.value || null)}
      />
    );
  }
  return (
    <input
      type="text"
      value={value ?? ''}
      maxLength={field.length || undefined}
      required={!field.nullable}
      onChange={(e) => onChange(e.target.value)}
    />
  );
}
