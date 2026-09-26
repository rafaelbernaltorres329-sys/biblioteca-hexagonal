package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {
    Usuario registrar(String nombre, String email);
}
