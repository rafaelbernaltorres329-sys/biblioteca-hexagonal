package com.biblioteca.infrastructure.adapters.out.persistence;

import com.biblioteca.domain.model.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoJpaRepository extends JpaRepository<PrestamoEntity, Long> {
    List<PrestamoEntity> findByUsuarioIdAndEstadoIn(Long usuarioId, List<EstadoPrestamo> estados);
    List<PrestamoEntity> findByLibroIdAndEstadoIn(Long libroId, List<EstadoPrestamo> estados);
}
