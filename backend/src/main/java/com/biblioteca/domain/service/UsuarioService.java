package com.biblioteca.domain.service;

import com.biblioteca.domain.model.Usuario;
import com.biblioteca.domain.ports.in.ConsultarUsuariosUseCase;
import com.biblioteca.domain.ports.in.RegistrarUsuarioUseCase;
import com.biblioteca.domain.ports.out.UsuarioRepositoryPort;

import java.util.List;

public class UsuarioService implements RegistrarUsuarioUseCase, ConsultarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public UsuarioService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario registrar(String nombre, String email) {
        Usuario usuario = Usuario.nuevo(nombre, email);
        return usuarioRepository.guardar(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.buscarTodos();
    }
}
