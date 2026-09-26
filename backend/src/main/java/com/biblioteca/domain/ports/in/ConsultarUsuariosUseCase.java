package com.biblioteca.domain.ports.in;

import com.biblioteca.domain.model.Usuario;
import java.util.List;

public interface ConsultarUsuariosUseCase {
    List<Usuario> listarTodos();
}
