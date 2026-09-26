package com.biblioteca.infrastructure.adapters.in.web;

import com.biblioteca.domain.ports.in.ConsultarUsuariosUseCase;
import com.biblioteca.domain.ports.in.RegistrarUsuarioUseCase;
import com.biblioteca.infrastructure.adapters.in.web.dto.NuevoUsuarioRequest;
import com.biblioteca.infrastructure.adapters.in.web.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                              ConsultarUsuariosUseCase consultarUsuariosUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.consultarUsuariosUseCase = consultarUsuariosUseCase;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return consultarUsuariosUseCase.listarTodos().stream().map(UsuarioDTO::desde).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO crear(@Valid @RequestBody NuevoUsuarioRequest request) {
        var usuario = registrarUsuarioUseCase.registrar(request.nombre, request.email);
        return UsuarioDTO.desde(usuario);
    }
}
