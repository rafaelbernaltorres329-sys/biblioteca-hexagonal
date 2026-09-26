package com.biblioteca.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Prestamo {

    public static final int DIAS_PRESTAMO = 14;

    private Long id;
    private Long libroId;
    private Long usuarioId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;

    public Prestamo(Long id, Long libroId, Long usuarioId, LocalDate fechaPrestamo,
                     LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal,
                     EstadoPrestamo estado) {
        this.id = id;
        this.libroId = libroId;
        this.usuarioId = usuarioId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
    }

    public static Prestamo crear(Long libroId, Long usuarioId) {
        LocalDate hoy = LocalDate.now();
        return new Prestamo(null, libroId, usuarioId, hoy, hoy.plusDays(DIAS_PRESTAMO), null, EstadoPrestamo.ACTIVO);
    }

    public void devolver() {
        if (this.estado == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("Este prestamo ya fue devuelto");
        }
        this.fechaDevolucionReal = LocalDate.now();
        this.estado = EstadoPrestamo.DEVUELTO;
    }

    public void actualizarEstadoSiVencido() {
        if (this.estado == EstadoPrestamo.ACTIVO && LocalDate.now().isAfter(fechaDevolucionEsperada)) {
            this.estado = EstadoPrestamo.VENCIDO;
        }
    }

    public boolean estaActivo() {
        return this.estado == EstadoPrestamo.ACTIVO || this.estado == EstadoPrestamo.VENCIDO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLibroId() { return libroId; }
    public Long getUsuarioId() { return usuarioId; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public EstadoPrestamo getEstado() { return estado; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(id, prestamo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
