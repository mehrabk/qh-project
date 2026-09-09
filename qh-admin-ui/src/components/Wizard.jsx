import { useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api/client';
import { byEntityName, isAuditField, displayValue, normalizeFieldDefault } from '../data/meta';
import { buildExampleValue } from '../data/exampleValues';
import { invalidateFkCache } from '../hooks/useFkOptions';
import FieldInput, { fieldLabel } from './FieldInput';

function initialStepValues(entity) {
  const v = {};
  for (const f of entity.fields) {
    if (f.isId) continue;
    const def = normalizeFieldDefault(f.default);
    if (def === 'false') v[f.field] = false;
    else if (def === 'true') v[f.field] = true;
    else if (def && /^"/.test(def)) v[f.field] = def.replace(/^"|"$/g, '');
    else v[f.field] = null;
  }
  return v;
}

function visibleSteps(wizard, ctx) {
  return wizard.steps.filter((s) => !s.skipIf || !s.skipIf(ctx));
}

function StepForm({ step, entity, fixed, values, onChange, onFillExample }) {
  const lockedNames = new Set(Object.keys(fixed));
  const editableFields = entity.fields.filter(
    (f) => !f.isId && !isAuditField(f.field) && !lockedNames.has(f.field)
  );

  function getFieldValue(field) {
    const raw = values[field.field];
    if (field.widget === 'fk') return raw && typeof raw === 'object' ? raw.id : raw;
    return raw;
  }

  function handleFieldChange(field, newValue) {
    if (field.widget === 'fk') {
      onChange(field.field, newValue ? { id: newValue } : null);
    } else {
      onChange(field.field, newValue);
    }
  }

  return (
    <>
      {lockedNames.size > 0 && (
        <div className="wizard-locked-fields">
          {Object.entries(fixed).map(([name, value]) => {
            const f = entity.fields.find((x) => x.field === name);
            const target = f && f.fkTarget ? byEntityName[f.fkTarget] : null;
            const label = value && typeof value === 'object' && value.id != null
              ? (typeof value.id === 'number' ? `#${value.id}` : String(value.id))
              : String(value);
            return (
              <span className="wizard-locked-chip" key={name}>
                🔗 {f ? fieldLabel(f) : name}: {target ? `${target.title} ${label}` : label}
              </span>
            );
          })}
        </div>
      )}
      <div className="form-toolbar">
        <button type="button" className="btn btn-ghost" onClick={onFillExample}>
          ✨ پر کردن با مقادیر نمونه
        </button>
      </div>
      <div className="form-grid">
        {editableFields.map((f) => (
          <div className="form-field" key={f.field}>
            <label>
              {fieldLabel(f)}
              {!f.nullable && <span className="req">*</span>}
              <span className="col-hint">{f.column}</span>
            </label>
            <FieldInput field={f} value={getFieldValue(f)} onChange={(v) => handleFieldChange(f, v)} />
          </div>
        ))}
      </div>
    </>
  );
}

export default function Wizard({ wizard }) {
  const [active, setActive] = useState(false);
  const [stepIndex, setStepIndex] = useState(0);
  const [ctx, setCtx] = useState({});
  const [values, setValues] = useState(() => initialStepValues(byEntityName[wizard.steps[0].entity]));
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [memberCount, setMemberCount] = useState(0);

  const steps = visibleSteps(wizard, ctx);
  const step = steps[stepIndex];
  const finished = stepIndex >= steps.length;
  const entity = step ? byEntityName[step.entity] : null;
  const fixed = step && step.fixedValues ? step.fixedValues(ctx) : {};

  function start() {
    setActive(true);
    setStepIndex(0);
    setCtx({});
    setValues(initialStepValues(byEntityName[wizard.steps[0].entity]));
    setError(null);
    setMemberCount(0);
  }

  function reset() {
    setActive(false);
    setStepIndex(0);
    setCtx({});
    setError(null);
  }

  function goToStep(index, nextCtx) {
    setStepIndex(index);
    const nextSteps = visibleSteps(wizard, nextCtx ?? ctx);
    const nextStep = nextSteps[index];
    if (nextStep) setValues(initialStepValues(byEntityName[nextStep.entity]));
  }

  function fillExample() {
    setValues((v) => {
      const next = { ...v };
      const lockedNames = new Set(Object.keys(fixed));
      for (const f of entity.fields) {
        if (f.isId || isAuditField(f.field) || lockedNames.has(f.field)) continue;
        if (f.widget === 'fk') continue; // leave FK pickers to the user - example ids rarely exist
        next[f.field] = buildExampleValue(f);
      }
      return next;
    });
  }

  async function submitStep(advance) {
    if (step.validate) {
      const message = step.validate(values, fixed, ctx);
      if (message) {
        setError(message);
        return;
      }
    }
    setSaving(true);
    setError(null);
    try {
      const payload = { ...values, ...fixed };
      const created = await api.create(entity.path, payload);
      invalidateFkCache(step.entity);
      setCtx((prev) => {
        const next = { ...prev };
        if (step.repeatable) {
          next[step.key] = [...(prev[step.key] || []), created];
        } else {
          next[step.key] = created;
        }
        return next;
      });
      if (step.repeatable && !advance) {
        setMemberCount((n) => n + 1);
        setValues(initialStepValues(entity));
      } else {
        goToStep(stepIndex + 1, { ...ctx, [step.key]: created });
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  async function runAction() {
    setSaving(true);
    setError(null);
    try {
      const result = await step.run(ctx);
      invalidateFkCache(step.entity);
      setCtx((prev) => ({ ...prev, [step.key]: result }));
      goToStep(stepIndex + 1, { ...ctx, [step.key]: result });
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  }

  function skipStep() {
    goToStep(stepIndex + 1, ctx);
  }

  function handleStepSubmit(e) {
    e.preventDefault();
    const repeat = e.nativeEvent.submitter?.value === 'repeat';
    submitStep(!repeat);
  }

  if (!active) {
    return (
      <div className="wizard-card">
        <h3>{wizard.title}</h3>
        <p className="muted">{wizard.description}</p>
        <button className="btn btn-primary" onClick={start}>
          ▶ شروع ویزارد
        </button>
      </div>
    );
  }

  return (
    <div className="wizard-card wizard-card-active">
      <div className="wizard-header">
        <h3>{wizard.title}</h3>
        <button type="button" className="icon-btn" onClick={reset}>✕</button>
      </div>

      <ol className="wizard-progress">
        {steps.map((s, i) => (
          <li
            key={s.key}
            className={i < stepIndex ? 'wizard-step-done' : i === stepIndex ? 'wizard-step-current' : ''}
          >
            {s.label}
            {ctx[s.key] && (
              <span className="step-result">
                {' '}✔ {Array.isArray(ctx[s.key]) ? `${ctx[s.key].length} مورد` : `#${ctx[s.key].id}`}
              </span>
            )}
          </li>
        ))}
      </ol>

      {!finished && step && step.kind === 'action' && (
        <div className="wizard-step-body">
          <h4>{step.label}</h4>
          {step.help && <p className="muted wizard-step-help">{step.help}</p>}
          {step.summary && <p className="wizard-action-summary">{step.summary(ctx)}</p>}
          {error && <p className="field-error">{error}</p>}
          <div className="wizard-actions">
            {step.optional && (
              <button type="button" className="btn btn-ghost" onClick={skipStep} disabled={saving}>
                رد کردن این مرحله
              </button>
            )}
            <button type="button" className="btn btn-primary" onClick={runAction} disabled={saving}>
              {saving ? 'در حال انجام...' : 'انجام این مرحله ▶'}
            </button>
          </div>
        </div>
      )}

      {!finished && step && step.kind !== 'action' && (
        <div className="wizard-step-body">
          <h4>{step.label}</h4>
          {step.help && <p className="muted wizard-step-help">{step.help}</p>}
          {step.repeatable && memberCount > 0 && (
            <p className="wizard-step-help">تاکنون {memberCount} مورد اضافه شده - می‌توانید مورد دیگری اضافه کنید یا ادامه دهید.</p>
          )}
          <form onSubmit={handleStepSubmit}>
            <StepForm step={step} entity={entity} fixed={fixed} values={values} onChange={(f, v) => setValues((old) => ({ ...old, [f]: v }))} onFillExample={fillExample} />
            {error && <p className="field-error">{error}</p>}
            <div className="wizard-actions">
              {step.optional && (
                <button type="button" className="btn btn-ghost" onClick={skipStep} disabled={saving}>
                  رد کردن این مرحله
                </button>
              )}
              {step.repeatable && (
                <button type="submit" name="action" value="repeat" className="btn btn-ghost" disabled={saving}>
                  {saving ? 'در حال ذخیره...' : '+ افزودن مورد دیگر'}
                </button>
              )}
              <button type="submit" name="action" value="advance" className="btn btn-primary" disabled={saving}>
                {saving ? 'در حال ذخیره...' : step.repeatable ? 'پایان و ادامه' : 'ثبت و مرحله بعد ▶'}
              </button>
            </div>
          </form>
        </div>
      )}

      {finished && (
        <div className="wizard-review">
          <h4>✅ ویزارد کامل شد</h4>
          <ul className="wizard-review-list">
            {steps.map((s) => {
              const record = ctx[s.key];
              if (!record) return <li key={s.key} className="wizard-review-skipped">{s.label} — رد شد</li>;
              const target = byEntityName[s.entity];
              const records = Array.isArray(record) ? record : [record];
              return (
                <li key={s.key}>
                  {s.label}:{' '}
                  {records.map((r, i) => (
                    <span key={r.id}>
                      {i > 0 && '، '}
                      <Link to={`/e/${target.slug}`}>#{r.id} {displayValue(target, r)}</Link>
                    </span>
                  ))}
                </li>
              );
            })}
          </ul>
          <button type="button" className="btn btn-ghost" onClick={reset}>بستن</button>
          <button type="button" className="btn btn-primary" onClick={start}>ویزارد جدید</button>
        </div>
      )}
    </div>
  );
}
