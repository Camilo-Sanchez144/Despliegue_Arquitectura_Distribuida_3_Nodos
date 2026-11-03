// ÚNICO archivo que conoce los nombres del JSON del backend.
// Fuera de aquí, un libro siempre se ve así: { id, titulo, autor, categoria, fecha, cantidad, activo }

export function deApi(l) {
  return {
    id: l.id_libro,                    // ← verifica este nombre en la respuesta del GET
    titulo: l.titulo_libro,
    autor: l.autor_libro,
    categoria: l.categoria_libro,
    fecha: l.anio_publicacion_libro,
    cantidad: l.cantidad_libro,
    activo: l.status_libro,
  };
}

export function haciaApi(libro) {
  return {
    titulo_libro: libro.titulo,
    autor_libro: libro.autor,
    categoria_libro: libro.categoria,
    anio_publicacion_libro: libro.fecha,
    cantidad_libro: libro.cantidad,
    status_libro: libro.activo,
  };
}