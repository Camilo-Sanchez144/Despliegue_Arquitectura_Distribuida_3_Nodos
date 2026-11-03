const $ = (sel) => document.querySelector(sel);
const form = $('#form');
const modal = new bootstrap.Modal($('#modal'));

const esc = (s) =>
  String(s ?? '').replace(/[&<>"']/g, (c) =>
    ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));

// ───────── Alertas ─────────
export function mostrarAlerta(mensaje) {
  $('#alerta').innerHTML = `
    <div class="alert alert-danger alert-dismissible">${esc(mensaje)}
      <button class="btn-close" data-bs-dismiss="alert"></button>
    </div>`;
}
function formatearFecha(iso) {
  if (!iso) return '';
  const [anio, mes, dia] = String(iso).slice(0, 10).split('-');
  return `${dia}-${mes}-${anio}`;   // 26-10-2026
}
// ───────── Formulario / modal ─────────
// Los nombres del form (libroId, titulo, autor...) coinciden con los `name` del index.html

export function abrirModal(libro = null) {
  form.reset();
  $('#error-form').classList.add('d-none');
  $('#modal-titulo').textContent = libro ? 'Editar libro' : 'Nuevo libro';

  form.libroId.value = libro?.id ?? '';
  form.titulo.value = libro?.titulo ?? '';
  form.autor.value = libro?.autor ?? '';
  form.categoria.value = libro?.categoria ?? '';
  form.fecha.value = (libro?.fecha ?? '').slice(0, 10); // yyyy-MM-dd
  form.cantidad.value = libro?.cantidad ?? 1;

  modal.show();
}

export function cerrarModal() {
  modal.hide();
}

export function leerFormulario() {
  return {
    id: form.libroId.value,            // '' si es libro nuevo
    titulo: form.titulo.value.trim(),
    autor: form.autor.value.trim(),
    categoria: form.categoria.value.trim(),
    fecha: form.fecha.value,           // "1967-05-30"
    cantidad: Number(form.cantidad.value),
    activo: true,
  };
}

export function mostrarErrorForm(mensaje) {
  const box = $('#error-form');
  box.textContent = mensaje;
  box.classList.remove('d-none');
}

// ───────── Tabla y estadísticas ─────────

function botonesHtml(libro) {
  if (libro.activo) {
    return `
      <button class="btn btn-sm btn-outline-primary" data-accion="editar" data-id="${libro.id}" title="Editar">
        <i class="bi bi-pencil-square"></i></button>
      <button class="btn btn-sm btn-outline-danger" data-accion="eliminar" data-id="${libro.id}"
              data-titulo="${esc(libro.titulo)}" title="Desactivar"><i class="bi bi-trash"></i></button>`;
  }
  return `
    <button class="btn btn-sm btn-outline-success" data-accion="restaurar" data-id="${libro.id}" title="Reactivar">
      <i class="bi bi-arrow-counterclockwise"></i></button>`;
}

function filaHtml(libro) {
  const colorCantidad = libro.cantidad === 0 ? 'danger' : libro.cantidad <= 2 ? 'warning' : 'success';
  const estado = libro.activo
    ? '<span class="badge text-bg-success-subtle text-success-emphasis border border-success-subtle">Activo</span>'
    : '<span class="badge text-bg-secondary">Inactivo</span>';

  return `
    <tr class="${libro.activo ? '' : 'inactivo table-light text-muted'}">
      <td>${esc(libro.id)}</td>
      <td class="fw-semibold">${esc(libro.titulo)}</td>
      <td>${esc(libro.autor)}</td>
      <td><span class="badge text-bg-light border">${esc(libro.categoria)}</span></td>
      <td>${esc(formatearFecha(libro.fecha))}</td>
      <td class="text-center"><span class="badge text-bg-${colorCantidad}">${libro.cantidad}</span></td>
      <td class="text-center">${estado}</td>
      <td class="text-end">${botonesHtml(libro)}</td>
    </tr>`;
}

export function pintarTabla(libros) {
  $('#tabla').innerHTML = libros.map(filaHtml).join('');
  $('#vacio').classList.toggle('d-none', libros.length > 0);
}

export function pintarStats(activos, inactivos) {
  const ejemplares = activos.reduce((total, l) => total + l.cantidad, 0);
  const tarjeta = (icono, color, titulo, valor) => `
    <div class="col-md-4">
      <div class="card p-3 d-flex flex-row align-items-center gap-3">
        <div class="stat-icon bg-${color}-subtle text-${color}"><i class="bi bi-${icono}"></i></div>
        <div><div class="text-muted small">${titulo}</div><div class="fs-4 fw-bold">${valor}</div></div>
      </div>
    </div>`;

  $('#stats').innerHTML =
    tarjeta('journals', 'primary', 'Títulos activos', activos.length) +
    tarjeta('stack', 'success', 'Ejemplares', ejemplares) +
    tarjeta('archive', 'danger', 'Inactivos', inactivos.length);
}