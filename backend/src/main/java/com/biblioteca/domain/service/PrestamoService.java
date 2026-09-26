package com.biblioteca.domain.service;

import com.biblioteca.domain.exception.LibroNoDisponibleException;
import com.biblioteca.domain.exception.LimitePrestamosExcedidoException;
import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.domain.model.Libro;
import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.domain.model.Usuario;
import com.biblioteca.domain.ports.in.ConsultarPrestamosUseCase;
import com.biblioteca.domain.ports.in.DevolverLibroUseCase;
import com.biblioteca.domain.ports.in.PrestarLibroUseCase;
import com.biblioteca.domain.ports.out.LibroRepositoryPort;
import com.biblioteca.domain.ports.out.PrestamoRepositoryPort;
import com.biblioteca.domain.ports.out.UsuarioRepositoryPort;

import java.util.List;

/**
 * Nucleo de las reglas de negocio del prestamo de libros.
 * Depende SOLO de interfaces (ports), nunca de detalles de infraestructura.
 */
public class PrestamoService implements PrestarLibroUseCase, DevolverLibroUseCase, ConsultarPrestamosUseCase {

    private static final int MAX_PRESTAMOS_ACTIVOS_POR_USUARIO = 3;

    private final PrestamoRepositoryPort prestamoRepository;
    private final LibroRepositoryPort libroRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public PrestamoService(PrestamoRepositoryPort prestamoRepository,
                            LibroRepositoryPort libroRepository,
                            UsuarioRepositoryPort usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Prestamo prestar(Long libroId, Long usuarioId) {
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + usuarioId));

        Libro libro = libroRepository.buscarPorId(libroId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + libroId));

        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException("El libro '" + libro.getTitulo() + "' no esta disponible");
        }

        List<Prestamo> prestamosActivos = prestamoRepository.buscarActivosPorUsuario(usuario.getId());
        if (prestamosActivos.size() >= MAX_PRESTAMOS_ACTIVOS_POR_USUARIO) {
            throw new LimitePrestamosExcedidoException(
                    "El usuario ya alcanzo el limite de " + MAX_PRESTAMOS_ACTIVOS_POR_USUARIO + " prestamos activos");
        }

        libro.marcarComoPrestado();
        libroRepository.guardar(libro);

        Prestamo prestamo = Prestamo.crear(libro.getId(), usuario.getId());
        return prestamoRepository.guardar(prestamo);
    }

    @Override
    public Prestamo devolver(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.buscarPorId(prestamoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Prestamo no encontrado con id: " + prestamoId));

        prestamo.devolver();
        prestamoRepository.guardar(prestamo);

        Libro libro = libroRepository.buscarPorId(prestamo.getLibroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id: " + prestamo.getLibroId()));
        libro.marcarComoDisponible();
        libroRepository.guardar(libro);

        return prestamo;
    }

    @Override
    public List<Prestamo> listarTodos() {
        List<Prestamo> prestamos = prestamoRepository.buscarTodos();
        prestamos.forEach(Prestamo::actualizarEstadoSiVencido);
        return prestamos;
    }
}
