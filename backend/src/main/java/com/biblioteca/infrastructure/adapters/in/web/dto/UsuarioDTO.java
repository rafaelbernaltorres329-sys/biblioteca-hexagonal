package com.biblioteca.infrastructure.adapters.in.web.dto;

import com.biblioteca.domain.model.Usuario;

public class UsuarioDTO {
    public Long id;
    public String nombre;
    public String email;

    public static UsuarioDTO desde(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = usuario.getId();
        dto.nombre = usuario.getNombre();
        dto.email = usuario.getEmail();
        return dto;
    }
}
