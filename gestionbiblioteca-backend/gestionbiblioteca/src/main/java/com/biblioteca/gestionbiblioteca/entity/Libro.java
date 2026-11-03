package com.biblioteca.gestionbiblioteca.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity()
@Table(name = "libros")
public class Libro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id_libro;

    @Column(name = "titulo_libro")
    private String titulo_libro;

    @Column(name = "autor_libro")
    private String autorlibro;

    @Column(name = "categoria_libro")
    private String categoria_libro;

    @Column(name="anio_publicacion_libro")
    private Date anio_publicacion_libro;

    @Column(name="cantidad_libro")
    private int cantidad_libro;

    @Column(name="status_libro")
    private boolean status_libro;

    public Long getId_libro() {
        return id_libro;
    }

    public void setId_libro(Long id_libro) {
        this.id_libro = id_libro;
    }

    public String getAutor_libro() {
        return autorlibro;
    }

    public void setAutor_libro(String autor_libro) {
        this.autorlibro = autor_libro;
    }

    public String getCategoria_libro() {
        return categoria_libro;
    }

    public void setCategoria_libro(String categoria_libro) {
        this.categoria_libro = categoria_libro;
    }

    public Date getAnio_publicacion_libro() {
        return anio_publicacion_libro;
    }

    public void setAnio_publicacion_libro(Date anio_publicacion_libro) {
        this.anio_publicacion_libro = anio_publicacion_libro;
    }

    public int getCantidad_libro() {
        return cantidad_libro;
    }

    public void setCantidad_libro(int cantidad_libro) {
        this.cantidad_libro = cantidad_libro;
    }

    public boolean isStatus_libro() {
        return status_libro;
    }

    public void setStatus_libro(boolean status_libro) {
        this.status_libro = status_libro;
    }

    public String getTitulo_libro() {
        return titulo_libro;
    }

    public void setTitulo_libro(String titulo_libro) {
        this.titulo_libro = titulo_libro;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id_libro=" + id_libro +
                ", titulo_libro='" + titulo_libro + '\'' +
                ", autor_libro='" + autorlibro + '\'' +
                ", categoria_libro='" + categoria_libro + '\'' +
                ", anio_publicacion_libro=" + anio_publicacion_libro +
                ", cantidad_libro=" + cantidad_libro +
                ", status_libro=" + status_libro +
                '}';
    }
}
