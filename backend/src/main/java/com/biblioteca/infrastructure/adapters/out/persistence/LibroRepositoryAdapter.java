package com.biblioteca.infrastructure.adapters.out.persistence;

import com.biblioteca.domain.model.Libro;
import com.biblioteca.domain.ports.out.LibroRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida: implementa el puerto del dominio usando JPA.
 * Traduce entre Libro (dominio) y LibroEntity (persistencia).
 */
@Component
public class LibroRepositoryAdapter implements LibroRepositoryPort {

    private final LibroJpaRepository jpaRepository;

    public LibroRepositoryAdapter(LibroJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Libro guardar(Libro libro) {
        LibroEntity entity = toEntity(libro);
        LibroEntity guardada = jpaRepository.save(entity);
        return toDomain(guardada);
    }

    @Override
    public Optional<Libro> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Libro> buscarTodos() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }

    private LibroEntity toEntity(Libro libro) {
        return new LibroEntity(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.isDisponible());
    }

    private Libro toDomain(LibroEntity entity) {
        return new Libro(entity.getId(), entity.getTitulo(), entity.getAutor(), entity.getIsbn(), entity.isDisponible());
    }
}
