package com.biblioteca.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NuevoUsuarioRequest {
    @NotBlank
    public String nombre;
    @NotBlank
    @Email
    public String email;
}
