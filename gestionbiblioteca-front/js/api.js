import { API_URL } from './config.js';
import { deApi, haciaApi } from './libro.js';

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  if (res.status === 204) return null;

  const data = await res.json().catch(() => null);
  if (!res.ok) throw new Error(data?.message || data?.error || `Error ${res.status}`);
  return data;
}

export const api = {
  // GET /api/libros
  async listarActivos() {
    return (await request(API_URL)).map(deApi);
  },
  // GET /api/libros/inactivos
  async listarInactivos() {
    return (await request(`${API_URL}/inactivos`)).map(deApi);
  },
  // GET /api/libros/{id}
  async obtener(id) {
    return deApi(await request(`${API_URL}/${id}`));
  },
  // POST /api/libros
  crear(libro) {
    return request(API_URL, { method: 'POST', body: JSON.stringify(haciaApi(libro)) });
  },
  // PUT /api/libros/{id}
  actualizar(id, libro) {
    return request(`${API_URL}/${id}`, { method: 'PUT', body: JSON.stringify(haciaApi(libro)) });
  },
  // PUT /api/libros/{id}/activar
  activar(id, libro) {
    return request(`${API_URL}/${id}/activar`, { method: 'PUT', body: JSON.stringify(haciaApi(libro)) });
  },
  // DELETE /api/libros/{id}
  eliminar(id) {
    return request(`${API_URL}/${id}`, { method: 'DELETE' });
  },
};