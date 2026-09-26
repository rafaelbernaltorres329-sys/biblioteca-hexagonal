package com.biblioteca.infrastructure.adapters.in.web.dto;

import com.biblioteca.domain.model.Libro;

public class LibroDTO {
    public Long id;
    public String titulo;
    public String autor;
    public String isbn;
    public boolean disponible;

    public static LibroDTO desde(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.id = libro.getId();
        dto.titulo = libro.getTitulo();
        dto.autor = libro.getAutor();
        dto.isbn = libro.getIsbn();
        dto.disponible = libro.isDisponible();
        return dto;
    }
}
