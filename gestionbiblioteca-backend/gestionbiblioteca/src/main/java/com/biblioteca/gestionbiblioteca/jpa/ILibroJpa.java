package com.biblioteca.gestionbiblioteca.jpa;

import com.biblioteca.gestionbiblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ILibroJpa extends JpaRepository<Libro, Long> {
    List<Libro> findByAutorlibro(String autor);
    @Query("SELECT l FROM Libro l WHERE l.status_libro = false")
    List<Libro> buscarInactivos();
    @Query("SELECT l FROM Libro l WHERE l.status_libro = true")
    List<Libro> buscarActivos();
}