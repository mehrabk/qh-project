import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { bySlug } from '../data/meta';
import { api } from '../api/client';
import { invalidateFkCache } from '../hooks/useFkOptions';
import EntityTable from '../components/EntityTable';
import EntityForm from '../components/EntityForm';
import { useToast } from '../components/Toast';

export default function EntityPage() {
  const { slug } = useParams();
  const entity = bySlug[slug];
  const toast = useToast();

  const [items, setItems] = useState(null);
  const [editing, setEditing] = useState(null); // null = closed, {} = create, item = edit
  const [saving, setSaving] = useState(false);

  function reload() {
    if (!entity) return;
    setItems(null);
    api
      .findAll(entity.path)
      .then(setItems)
      .catch((err) => {
        toast(`خطا در بارگذاری ${entity.title}: ${err.message}`, 'error');
        setItems([]);
      });
  }

  useEffect(() => {
    reload();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [slug]);

  async function handleSubmit(payload) {
    setSaving(true);
    try {
      if (editing.id) {
        await api.update(entity.path, editing.id, payload);
        toast(`${entity.title} #${editing.id} به‌روزرسانی شد`, 'success');
      } else {
        const created = await api.create(entity.path, payload);
        toast(`${entity.title} با شناسه #${created?.id ?? '?'} ایجاد شد`, 'success');
      }
      invalidateFkCache(entity.entity);
      setEditing(null);
      reload();
    } catch (err) {
      toast(`خطا: ${err.message}`, 'error');
    } finally {
      setSaving(false);
    }
  }

  async function handleDelete(item) {
    if (!confirm(`رکورد #${item.id} از ${entity.title} حذف شود؟`)) return;
    try {
      await api.remove(entity.path, item.id);
      toast(`رکورد #${item.id} حذف شد`, 'success');
      invalidateFkCache(entity.entity);
      reload();
    } catch (err) {
      toast(`خطا در حذف: ${err.message}`, 'error');
    }
  }

  if (!entity) {
    return <div className="empty-state">موجودیتی با این آدرس پیدا نشد.</div>;
  }

  return (
    <div className="entity-page">
      <div className="page-header">
        <div>
          <h2>{entity.title}</h2>
          <p className="muted">
            <code>{entity.path}</code> · جدول <code>{entity.schema}.{entity.table}</code>
            {entity.readonly && <span className="badge">فقط خواندنی</span>}
          </p>
        </div>
        {!entity.readonly && (
          <button className="btn btn-primary" onClick={() => setEditing({})}>
            + رکورد جدید
          </button>
        )}
      </div>

      <EntityTable entity={entity} items={items} onEdit={setEditing} onDelete={handleDelete} />

      {editing && (
        <EntityForm
          entity={entity}
          item={editing.id ? editing : null}
          saving={saving}
          onCancel={() => setEditing(null)}
          onSubmit={handleSubmit}
        />
      )}
    </div>
  );
}
