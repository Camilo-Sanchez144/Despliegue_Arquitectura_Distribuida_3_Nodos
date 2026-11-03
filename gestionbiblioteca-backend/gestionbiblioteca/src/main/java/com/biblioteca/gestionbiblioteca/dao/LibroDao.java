package com.biblioteca.gestionbiblioteca.dao;

import com.biblioteca.gestionbiblioteca.entity.Libro;
import com.biblioteca.gestionbiblioteca.jpa.ILibroJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LibroDao implements ILibroDao{
    private final ILibroJpa libroJPA;

    public LibroDao(ILibroJpa libroJPA) {
        this.libroJPA = libroJPA;
    }

    @Override
    public Libro guardar(Libro libro) {
        return libroJPA.save(libro);
    }
    @Override
    public List<Libro> listarActivos() {
        return libroJPA.buscarActivos();
    }

    @Override
    public List<Libro> listarInactivos() {
        return libroJPA.buscarInactivos();
    }

    @Override
    public Optional<Libro> buscarPorId(Long id) {
        return libroJPA.findById(id);
    }

    @Override
    public void activar(Long id) {
        Libro libro = libroJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
        libro.setStatus_libro(true);
        libroJPA.save(libro);
    }

    @Override
    public boolean existePorId(Long id) {
        return libroJPA.existsById(id);
    }

    @Override
    public void eliminar(Long id) {
        Libro libro = libroJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
        libro.setStatus_libro(false);
        libroJPA.save(libro);
    }

    @Override
    public List<Libro> buscarPorAutor(String autor) {
        return libroJPA.findByAutorlibro(autor);
    }
}
