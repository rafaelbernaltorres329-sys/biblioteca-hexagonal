package com.biblioteca.domain.ports.out;

import com.biblioteca.domain.model.Prestamo;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepositoryPort {
    Prestamo guardar(Prestamo prestamo);
    Optional<Prestamo> buscarPorId(Long id);
    List<Prestamo> buscarTodos();
    List<Prestamo> buscarActivosPorUsuario(Long usuarioId);
    List<Prestamo> buscarActivosPorLibro(Long libroId);
}
