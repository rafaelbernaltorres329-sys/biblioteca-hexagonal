package com.biblioteca.infrastructure.adapters.out.persistence;

import com.biblioteca.domain.model.EstadoPrestamo;
import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.domain.ports.out.PrestamoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PrestamoRepositoryAdapter implements PrestamoRepositoryPort {

    private static final List<EstadoPrestamo> ESTADOS_ACTIVOS = List.of(EstadoPrestamo.ACTIVO, EstadoPrestamo.VENCIDO);

    private final PrestamoJpaRepository jpaRepository;

    public PrestamoRepositoryAdapter(PrestamoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Prestamo guardar(Prestamo prestamo) {
        PrestamoEntity entity = toEntity(prestamo);
        PrestamoEntity guardado = jpaRepository.save(entity);
        return toDomain(guardado);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Prestamo> buscarActivosPorUsuario(Long usuarioId) {
        return jpaRepository.findByUsuarioIdAndEstadoIn(usuarioId, ESTADOS_ACTIVOS)
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Prestamo> buscarActivosPorLibro(Long libroId) {
        return jpaRepository.findByLibroIdAndEstadoIn(libroId, ESTADOS_ACTIVOS)
                .stream().map(this::toDomain).toList();
    }

    private PrestamoEntity toEntity(Prestamo p) {
        return new PrestamoEntity(p.getId(), p.getLibroId(), p.getUsuarioId(), p.getFechaPrestamo(),
                p.getFechaDevolucionEsperada(), p.getFechaDevolucionReal(), p.getEstado());
    }

    private Prestamo toDomain(PrestamoEntity e) {
        return new Prestamo(e.getId(), e.getLibroId(), e.getUsuarioId(), e.getFechaPrestamo(),
                e.getFechaDevolucionEsperada(), e.getFechaDevolucionReal(), e.getEstado());
    }
}
