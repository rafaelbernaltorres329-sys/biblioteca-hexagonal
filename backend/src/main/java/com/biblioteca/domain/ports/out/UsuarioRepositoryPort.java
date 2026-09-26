package com.biblioteca.domain.ports.out;

import com.biblioteca.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    List<Usuario> buscarTodos();
    boolean existePorId(Long id);
}
