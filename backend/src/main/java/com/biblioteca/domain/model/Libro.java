package com.biblioteca.domain.model;

import java.util.Objects;

/**
 * Entidad de dominio. No conoce nada de JPA, HTTP ni ningun framework:
 * es el corazon puro de la Arquitectura Hexagonal.
 */
public class Libro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    public Libro(Long id, String titulo, String autor, String isbn, boolean disponible) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo del libro no puede estar vacio");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("El autor del libro no puede estar vacio");
        }
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    public static Libro nuevo(String titulo, String autor, String isbn) {
        return new Libro(null, titulo, autor, isbn, true);
    }

    public void marcarComoPrestado() {
        if (!this.disponible) {
            throw new IllegalStateException("El libro ya esta prestado");
        }
        this.disponible = false;
    }

    public void marcarComoDisponible() {
        this.disponible = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public boolean isDisponible() { return disponible; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return Objects.equals(id, libro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
