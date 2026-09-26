package com.biblioteca.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroJpaRepository extends JpaRepository<LibroEntity, Long> {
}
