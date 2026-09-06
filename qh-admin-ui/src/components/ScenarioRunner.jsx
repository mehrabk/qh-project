import { useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api/client';
import { byEntityName } from '../data/meta';
import { invalidateFkCache } from '../hooks/useFkOptions';

async function firstOf(entityName) {
  const target = byEntityName[entityName];
  const items = await api.findAll(target.path);
  if (!items.length) {
    throw new Error(`هیچ رکورد از پیش موجودی برای ${entityName} پیدا نشد - ابتدا یکی بسازید.`);
  }
  return items[0];
}

export default function ScenarioRunner({ scenario }) {
  const [log, setLog] = useState([]);
  const [running, setRunning] = useState(false);
  const [ctx, setCtx] = useState(null);

  async function run() {
    setRunning(true);
    setLog([]);
    const context = {};
    for (const step of scenario.steps) {
      setLog((l) => [...l, { text: step.label, status: 'running' }]);
      try {
        const payload = await step.build(context, { first: firstOf });
        const entity = byEntityName[step.entity];
        const created = await api.create(entity.path, payload);
        context[step.saveAs] = created;
        invalidateFkCache(step.entity);
        setLog((l) => l.map((x, i) => (i === l.length - 1 ? { ...x, status: 'done', id: created?.id } : x)));
      } catch (err) {
        setLog((l) => l.map((x, i) => (i === l.length - 1 ? { ...x, status: 'error', error: err.message } : x)));
        setRunning(false);
        setCtx(context);
        return;
      }
    }
    setCtx(context);
    setRunning(false);
  }

  return (
    <div className="scenario-card">
      <h3>{scenario.title}</h3>
      <p className="muted">{scenario.description}</p>
      <ol className="scenario-steps">
        {scenario.steps.map((s, i) => {
          const entry = log[i];
          return (
            <li key={i} className={entry ? `step-${entry.status}` : ''}>
              {s.label}
              {entry?.status === 'running' && <span className="spinner" />}
              {entry?.status === 'done' && (
                <span className="step-result">
                  {' '}✔ ایجاد شد (
                  <Link to={`/e/${byEntityName[s.entity].slug}`}>#{entry.id}</Link>)
                </span>
              )}
              {entry?.status === 'error' && <span className="step-result error"> ✕ {entry.error}</span>}
            </li>
          );
        })}
      </ol>
      <button className="btn btn-primary" onClick={run} disabled={running}>
        {running ? 'در حال اجرا...' : '▶ اجرای سناریو'}
      </button>
    </div>
  );
}
