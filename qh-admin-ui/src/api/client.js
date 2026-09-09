// Thin wrapper around the backend's uniform ApiResponse<T> envelope:
// { success, data, message, timestamp }. Every controller in every module
// (Product Builder's five sub-modules + Party) returns this same shape.

async function request(path, options = {}) {
  const res = await fetch(path, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  });

  let body = null;
  const text = await res.text();
  if (text) {
    try {
      body = JSON.parse(text);
    } catch {
      throw new Error(`پاسخ نامعتبر از سرور (HTTP ${res.status})`);
    }
  }

  if (!res.ok) {
    const message = body?.message || `خطای HTTP ${res.status}`;
    throw new Error(message);
  }
  if (body && body.success === false) {
    throw new Error(body.message || 'درخواست ناموفق بود');
  }
  return body ? body.data : null;
}

export const api = {
  findAll: (path) => request(path),
  findById: (path, id) => request(`${path}/${id}`),
  create: (path, entity) => request(path, { method: 'POST', body: JSON.stringify(entity) }),
  update: (path, id, entity) => request(`${path}/${id}`, { method: 'PUT', body: JSON.stringify(entity) }),
  remove: (path, id) => request(`${path}/${id}`, { method: 'DELETE' }),
  // Fires a lifecycle-transition endpoint (e.g. POST .../{id}/approve) that
  // isn't a plain create/update - no body, just the action itself.
  action: (path) => request(path, { method: 'POST' }),
};
