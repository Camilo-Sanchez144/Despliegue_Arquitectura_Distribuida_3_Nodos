import { api } from './api.js';
import * as vista from './vista.js';

// ───────── Cargar datos ─────────
async function recargar() {
  const [activos, inactivos] = await Promise.all([api.listarActivos(), api.listarInactivos()]);
  vista.pintarStats(activos, inactivos);
  vista.pintarTabla([...activos, ...inactivos]);
}

// Si un handler falla, muestra el error en pantalla en vez de romper en silencio
const conErrores = (handler) => async (...args) => {
  try {
    await handler(...args);
  } catch (e) {
    vista.mostrarAlerta(e.message);
  }
};

// ═════════ UN HANDLER POR BOTÓN ═════════

// Botón "Nuevo libro"
const onNuevo = () => vista.abrirModal();

// Botón ✏️ Editar  →  GET /api/libros/{id}
const onEditar = async (id) => {
  const libro = await api.obtener(id);
  vista.abrirModal(libro);
};

// Botón 🗑 Desactivar  →  DELETE /api/libros/{id}
const onEliminar = async (id, titulo) => {
  if (!confirm(`¿Desactivar «${titulo}»?`)) return;
  await api.eliminar(id);
  await recargar();
};

// Botón ↩ Reactivar  →  GET + PUT /api/libros/{id}  (no hay endpoint de restaurar)
const onRestaurar = async (id) => {
  const libro = await api.obtener(id);
  await api.activar(id, { ...libro, activo: true });
  await recargar();
};

// Botón "Guardar" del modal  →  POST /api/libros  (nuevo)  o  PUT /api/libros/{id}  (editar)
const onGuardar = async (evento) => {
  evento.preventDefault();
  const libro = vista.leerFormulario();

  try {
    if (libro.id) await api.actualizar(libro.id, libro);
    else await api.crear(libro);
  } catch (e) {
    vista.mostrarErrorForm(e.message);   // el error aparece dentro del modal
    return;
  }

  vista.cerrarModal();
  await recargar();
};

// ═════════ CONEXIÓN DE EVENTOS ═════════

document.querySelector('#btn-nuevo').addEventListener('click', conErrores(onNuevo));
document.querySelector('#form').addEventListener('submit', conErrores(onGuardar));

// Botones de la tabla: se delega en un solo listener porque las filas se crean dinámicamente
const accionesTabla = {
  editar: onEditar,
  eliminar: onEliminar,
  restaurar: onRestaurar,
};

document.querySelector('#tabla').addEventListener('click', (evento) => {
  const boton = evento.target.closest('button[data-accion]');
  if (!boton) return;
  const { accion, id, titulo } = boton.dataset;
  conErrores(accionesTabla[accion])(id, titulo);
});

// ═════════ ARRANQUE ═════════
conErrores(recargar)();