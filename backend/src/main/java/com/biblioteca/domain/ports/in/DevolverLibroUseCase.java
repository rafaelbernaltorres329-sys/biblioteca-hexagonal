package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Prestamo;

public interface DevolverLibroUseCase {
    Prestamo devolver(Long prestamoId);
}
