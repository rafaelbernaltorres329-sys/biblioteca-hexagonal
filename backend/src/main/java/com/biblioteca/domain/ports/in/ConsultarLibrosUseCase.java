package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Libro;
import java.util.List;

public interface ConsultarLibrosUseCase {
    List<Libro> listarTodos();
    Libro buscarPorId(Long id);
}
