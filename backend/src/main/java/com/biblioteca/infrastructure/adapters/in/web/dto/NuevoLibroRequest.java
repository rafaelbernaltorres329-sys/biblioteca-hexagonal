package com.biblioteca.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public class NuevoLibroRequest {
    @NotBlank
    public String titulo;
    @NotBlank
    public String autor;
    public String isbn;
}
