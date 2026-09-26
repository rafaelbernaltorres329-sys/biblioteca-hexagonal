package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Prestamo;
import java.util.List;

public interface ConsultarPrestamosUseCase {
    List<Prestamo> listarTodos();
}
