package com.biblioteca.infrastructure.adapters.in.web;

import com.biblioteca.domain.ports.in.ConsultarLibrosUseCase;
import com.biblioteca.domain.ports.in.RegistrarLibroUseCase;
import com.biblioteca.infrastructure.adapters.in.web.dto.LibroDTO;
import com.biblioteca.infrastructure.adapters.in.web.dto.NuevoLibroRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adaptador de entrada: traduce peticiones HTTP a llamadas de casos de uso.
 * No contiene logica de negocio, solo orquesta entrada/salida.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final RegistrarLibroUseCase registrarLibroUseCase;
    private final ConsultarLibrosUseCase consultarLibrosUseCase;

    public LibroController(RegistrarLibroUseCase registrarLibroUseCase,
                            ConsultarLibrosUseCase consultarLibrosUseCase) {
        this.registrarLibroUseCase = registrarLibroUseCase;
        this.consultarLibrosUseCase = consultarLibrosUseCase;
    }

    @GetMapping
    public List<LibroDTO> listar() {
        return consultarLibrosUseCase.listarTodos().stream().map(LibroDTO::desde).toList();
    }

    @GetMapping("/{id}")
    public LibroDTO obtener(@PathVariable Long id) {
        return LibroDTO.desde(consultarLibrosUseCase.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibroDTO crear(@Valid @RequestBody NuevoLibroRequest request) {
        var libro = registrarLibroUseCase.registrar(request.titulo, request.autor, request.isbn);
        return LibroDTO.desde(libro);
    }
}
