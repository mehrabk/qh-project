import { useState } from 'react';
import { fieldLabel } from './FieldInput';
import { byEntityName, displayValue, isAuditField } from '../data/meta';

function cellValue(field, item) {
  const raw = item[field.field];
  if (raw == null) return <span className="muted">—</span>;
  if (field.widget === 'fk') {
    const target = byEntityName[field.fkTarget];
    return target ? `#${raw.id} ${displayValue(target, raw)}` : `#${raw.id}`;
  }
  if (field.widget === 'checkbox') return raw ? '✔' : '—';
  if (typeof raw === 'object') return JSON.stringify(raw);
  return String(raw);
}

export default function EntityTable({ entity, items, onEdit, onDelete }) {
  const [showAudit, setShowAudit] = useState(false);
  const columns = entity.fields.filter((f) => showAudit || !isAuditField(f.field));

  if (!items) return <div className="panel-loading">در حال بارگذاری...</div>;
  if (items.length === 0) {
    return (
      <div className="empty-state">
        <p>هنوز رکوردی برای {entity.title} ثبت نشده است.</p>
      </div>
    );
  }

  return (
    <div className="table-wrap">
      <div className="table-toolbar">
        <label className="checkbox-label">
          <input type="checkbox" checked={showAudit} onChange={(e) => setShowAudit(e.target.checked)} />
          نمایش فیلدهای Audit
        </label>
        <span className="muted">{items.length} رکورد</span>
      </div>
      <div className="table-scroll">
        <table>
          <thead>
            <tr>
              {columns.map((f) => (
                <th key={f.field}>
                  {fieldLabel(f)}
                  <div className="col-hint">{f.column}</div>
                </th>
              ))}
              <th className="actions-col">عملیات</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.id}>
                {columns.map((f) => (
                  <td key={f.field}>{cellValue(f, item)}</td>
                ))}
                <td className="actions-col">
                  <button className="btn btn-small" onClick={() => onEdit(item)}>
                    {entity.readonly ? 'مشاهده' : 'ویرایش'}
                  </button>
                  {!entity.readonly && (
                    <button className="btn btn-small btn-danger" onClick={() => onDelete(item)}>
                      حذف
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
