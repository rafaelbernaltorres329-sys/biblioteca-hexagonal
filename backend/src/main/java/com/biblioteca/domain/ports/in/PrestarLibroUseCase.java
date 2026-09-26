package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Prestamo;

public interface PrestarLibroUseCase {
    Prestamo prestar(Long libroId, Long usuarioId);
}
