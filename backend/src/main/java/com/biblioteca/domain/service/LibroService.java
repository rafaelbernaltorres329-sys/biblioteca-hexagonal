package com.biblioteca.domain.service;

import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.domain.model.Libro;
import com.biblioteca.domain.ports.in.ConsultarLibrosUseCase;
import com.biblioteca.domain.ports.in.RegistrarLibroUseCase;
import com.biblioteca.domain.ports.out.LibroRepositoryPort;

import java.util.List;

/**
 * Implementa los puertos de entrada usando unicamente el puerto de salida (interfaz).
 * No conoce Spring, JPA ni HTTP: cumple con DIP (Dependency Inversion Principle).
 */
public class LibroService implements RegistrarLibroUseCase, ConsultarLibrosUseCase {

    private final LibroRepositoryPort libroRepository;

    public LibroService(LibroRepositoryPort libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro registrar(String titulo, String autor, String isbn) {
        Libro libro = Libro.nuevo(titulo, autor, isbn);
        return libroRepository.guardar(libro);
    }

    @Override
    public List<Libro> listarTodos() {
        return libroRepository.buscarTodos();
    }

    @Override
    public Libro buscarPorId(Long id) {
        return libroRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + id));
    }
}
