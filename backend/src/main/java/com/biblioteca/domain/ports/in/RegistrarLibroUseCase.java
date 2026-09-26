package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Libro;

public interface RegistrarLibroUseCase {
    Libro registrar(String titulo, String autor, String isbn);
}
