import { useEffect, useState } from 'react';
import { api } from '../api/client';
import { byEntityName } from '../data/meta';

const cache = new Map();

// Loads (and caches for the session) the list of records for an FK target
// entity, so <select> dropdowns can offer real, currently-existing rows.
export function useFkOptions(fkTarget) {
  const target = byEntityName[fkTarget];
  const [state, setState] = useState({ loading: true, error: null, items: cache.get(fkTarget) || [] });

  useEffect(() => {
    let cancelled = false;
    if (!target) {
      setState({ loading: false, error: `entity ${fkTarget} not found`, items: [] });
      return;
    }
    if (cache.has(fkTarget)) {
      setState({ loading: false, error: null, items: cache.get(fkTarget) });
      return;
    }
    setState((s) => ({ ...s, loading: true }));
    api
      .findAll(target.path)
      .then((items) => {
        if (cancelled) return;
        cache.set(fkTarget, items);
        setState({ loading: false, error: null, items });
      })
      .catch((err) => {
        if (cancelled) return;
        setState({ loading: false, error: err.message, items: [] });
      });
    return () => {
      cancelled = true;
    };
  }, [fkTarget]);

  return { ...state, targetEntity: target };
}

export function invalidateFkCache(entityName) {
  cache.delete(entityName);
}

export function getCachedFkOptions(entityName) {
  return cache.get(entityName) || [];
}
