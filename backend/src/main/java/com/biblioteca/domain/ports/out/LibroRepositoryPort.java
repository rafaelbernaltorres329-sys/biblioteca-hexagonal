package com.biblioteca.domain.ports.out;

import com.biblioteca.domain.model.Libro;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: el dominio define QUE necesita de la persistencia,
 * sin saber COMO se implementa (JPA, Mongo, en memoria, etc).
 */
public interface LibroRepositoryPort {
    Libro guardar(Libro libro);
    Optional<Libro> buscarPorId(Long id);
    List<Libro> buscarTodos();
    boolean existePorId(Long id);
}
