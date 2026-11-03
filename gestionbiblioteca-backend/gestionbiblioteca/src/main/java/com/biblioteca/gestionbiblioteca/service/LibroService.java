package com.biblioteca.gestionbiblioteca.service;

import com.biblioteca.gestionbiblioteca.dao.ILibroDao;
import com.biblioteca.gestionbiblioteca.entity.Libro;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LibroService {

    private final ILibroDao libroDao;

    public LibroService(ILibroDao libroDao) {
        this.libroDao = libroDao;
    }

    public Libro crear(Libro libro) {
        libro.setId_libro(null); // evita que el cliente sobrescriba un registro existente
        return libroDao.guardar(libro);
    }

    public List<Libro> listarActivos() {
        return libroDao.listarActivos();
    }

    public List<Libro> listarInactivos() {
        return libroDao.listarInactivos();
    }

    public Libro obtenerPorId(Long id) {
        return libroDao.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Libro no encontrado con id " + id));
    }

    public Libro actualizar(Long id, Libro datos) {
        Libro existente = obtenerPorId(id);
        existente.setTitulo_libro(datos.getTitulo_libro());
        existente.setAutor_libro(datos.getAutor_libro());
        existente.setCantidad_libro(datos.getCantidad_libro());
        existente.setCategoria_libro(datos.getCategoria_libro());
        existente.setAnio_publicacion_libro(datos.getAnio_publicacion_libro());
        return libroDao.guardar(existente);
    }
    public void activar(Long id) {
        if (!libroDao.existePorId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Libro no encontrado con id " + id);
        }
        libroDao.activar(id);
    }
    public void eliminar(Long id) {
        if (!libroDao.existePorId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Libro no encontrado con id " + id);
        }
        libroDao.eliminar(id);
    }
    public List<Libro> obtenerPorAutor(String autor){
        return libroDao.buscarPorAutor(autor);
    }
}
