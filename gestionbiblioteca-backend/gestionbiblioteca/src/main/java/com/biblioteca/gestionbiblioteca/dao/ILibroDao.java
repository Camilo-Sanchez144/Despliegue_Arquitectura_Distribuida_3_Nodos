package com.biblioteca.gestionbiblioteca.dao;

import com.biblioteca.gestionbiblioteca.entity.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroDao {
    Libro guardar(Libro libro);
    List<Libro> listarActivos();
    List<Libro> listarInactivos();
    Optional<Libro> buscarPorId(Long id);
    boolean existePorId(Long id);
    void eliminar(Long id);
    void activar(Long id);
    List<Libro> buscarPorAutor(String autor);
}
