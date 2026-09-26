package com.biblioteca.infrastructure.adapters.in.web.dto;

import com.biblioteca.domain.model.Prestamo;

public class PrestamoDTO {
    public Long id;
    public Long libroId;
    public Long usuarioId;
    public String fechaPrestamo;
    public String fechaDevolucionEsperada;
    public String fechaDevolucionReal;
    public String estado;

    public static PrestamoDTO desde(Prestamo p) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.id = p.getId();
        dto.libroId = p.getLibroId();
        dto.usuarioId = p.getUsuarioId();
        dto.fechaPrestamo = p.getFechaPrestamo().toString();
        dto.fechaDevolucionEsperada = p.getFechaDevolucionEsperada().toString();
        dto.fechaDevolucionReal = p.getFechaDevolucionReal() != null ? p.getFechaDevolucionReal().toString() : null;
        dto.estado = p.getEstado().toString();
        return dto;
    }
}
