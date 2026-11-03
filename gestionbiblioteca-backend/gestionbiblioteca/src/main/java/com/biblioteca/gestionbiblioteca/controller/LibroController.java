package com.biblioteca.gestionbiblioteca.controller;

import com.biblioteca.gestionbiblioteca.entity.Libro;
import com.biblioteca.gestionbiblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        Libro guardado = libroService.crear(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> actualizar(@PathVariable Long id) {
        libroService.activar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public List<Libro> listarActivos() {
        return libroService.listarActivos();
    }
    @GetMapping("/inactivos")
    public List<Libro> listarInactivos() {
        return libroService.listarInactivos();
    }
    @GetMapping("/{id}")
    public Libro obtener(@PathVariable Long id) {
        return libroService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.actualizar(id, libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}