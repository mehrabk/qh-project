import { useState } from 'react';
import FieldInput, { fieldLabel } from './FieldInput';
import { isAuditField } from '../data/meta';
import { buildExampleValue } from '../data/exampleValues';
import { getCachedFkOptions } from '../hooks/useFkOptions';

function initialValues(entity, item) {
  if (item) return { ...item };
  const v = {};
  for (const f of entity.fields) {
    if (f.isId) continue;
    if (f.default === 'false') v[f.field] = false;
    else if (f.default === 'true') v[f.field] = true;
    else if (f.default && /^"/.test(f.default)) v[f.field] = f.default.replace(/^"|"$/g, '');
    else v[f.field] = null;
  }
  return v;
}

export default function EntityForm({ entity, item, onCancel, onSubmit, saving }) {
  const [values, setValues] = useState(() => initialValues(entity, item));
  const [showAudit, setShowAudit] = useState(false);
  const isEdit = !!item;

  const editableFields = entity.fields.filter((f) => !f.isId && (showAudit || !isAuditField(f.field)));
  const readOnly = !!entity.readonly;

  function setField(field, value) {
    setValues((v) => ({ ...v, [field.field]: value }));
  }

  function getFieldValue(field) {
    const raw = values[field.field];
    if (field.widget === 'fk') return raw && typeof raw === 'object' ? raw.id : raw;
    return raw;
  }

  function handleFieldChange(field, newValue) {
    if (field.widget === 'fk') {
      setField(field, newValue ? { id: newValue } : null);
    } else {
      setField(field, newValue);
    }
  }

  function fillExamples() {
    setValues((v) => {
      const next = { ...v };
      for (const f of entity.fields) {
        if (f.isId || isAuditField(f.field)) continue;
        if (f.widget === 'fk') {
          const options = getCachedFkOptions(f.fkTarget);
          if (options.length > 0) next[f.field] = { id: options[0].id };
        } else {
          next[f.field] = buildExampleValue(f);
        }
      }
      return next;
    });
  }

  function handleSubmit(e) {
    e.preventDefault();
    const payload = { ...values };
    if (isEdit) payload.id = item.id;
    onSubmit(payload);
  }

  return (
    <div className="modal-backdrop" onMouseDown={(e) => e.target === e.currentTarget && onCancel()}>
      <div className="modal">
        <div className="modal-header">
          <h3>{isEdit ? `ویرایش — ${entity.title} #${item.id}` : `رکورد جدید — ${entity.title}`}</h3>
          <button type="button" className="icon-btn" onClick={onCancel}>✕</button>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="form-toolbar">
            {!readOnly && (
              <button type="button" className="btn btn-ghost" onClick={fillExamples}>
                ✨ پر کردن با مقادیر نمونه
              </button>
            )}
            <label className="checkbox-label">
              <input type="checkbox" checked={showAudit} onChange={(e) => setShowAudit(e.target.checked)} />
              نمایش فیلدهای Audit
            </label>
          </div>
          <div className="form-grid">
            {editableFields.map((f) => (
              <div className="form-field" key={f.field}>
                <label>
                  {fieldLabel(f)}
                  {!f.nullable && <span className="req">*</span>}
                  <span className="col-hint">{f.column}</span>
                </label>
                <fieldset disabled={readOnly} className="fieldset-plain">
                  <FieldInput field={f} value={getFieldValue(f)} onChange={(v) => handleFieldChange(f, v)} />
                </fieldset>
              </div>
            ))}
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-ghost" onClick={onCancel}>{readOnly ? 'بستن' : 'انصراف'}</button>
            {!readOnly && (
              <button type="submit" className="btn btn-primary" disabled={saving}>
                {saving ? 'در حال ذخیره...' : isEdit ? 'ذخیره تغییرات' : 'ایجاد رکورد'}
              </button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}
