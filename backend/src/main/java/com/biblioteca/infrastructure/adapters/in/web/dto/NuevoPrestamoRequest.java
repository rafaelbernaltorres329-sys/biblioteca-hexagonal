package com.biblioteca.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotNull;

public class NuevoPrestamoRequest {
    @NotNull
    public Long libroId;
    @NotNull
    public Long usuarioId;
}
