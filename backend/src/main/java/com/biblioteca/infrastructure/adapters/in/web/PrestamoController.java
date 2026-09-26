package com.biblioteca.infrastructure.adapters.in.web;

import com.biblioteca.domain.ports.in.ConsultarPrestamosUseCase;
import com.biblioteca.domain.ports.in.DevolverLibroUseCase;
import com.biblioteca.domain.ports.in.PrestarLibroUseCase;
import com.biblioteca.infrastructure.adapters.in.web.dto.NuevoPrestamoRequest;
import com.biblioteca.infrastructure.adapters.in.web.dto.PrestamoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestarLibroUseCase prestarLibroUseCase;
    private final DevolverLibroUseCase devolverLibroUseCase;
    private final ConsultarPrestamosUseCase consultarPrestamosUseCase;

    public PrestamoController(PrestarLibroUseCase prestarLibroUseCase,
                               DevolverLibroUseCase devolverLibroUseCase,
                               ConsultarPrestamosUseCase consultarPrestamosUseCase) {
        this.prestarLibroUseCase = prestarLibroUseCase;
        this.devolverLibroUseCase = devolverLibroUseCase;
        this.consultarPrestamosUseCase = consultarPrestamosUseCase;
    }

    @GetMapping
    public List<PrestamoDTO> listar() {
        return consultarPrestamosUseCase.listarTodos().stream().map(PrestamoDTO::desde).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrestamoDTO prestar(@Valid @RequestBody NuevoPrestamoRequest request) {
        var prestamo = prestarLibroUseCase.prestar(request.libroId, request.usuarioId);
        return PrestamoDTO.desde(prestamo);
    }

    @PutMapping("/{id}/devolver")
    public PrestamoDTO devolver(@PathVariable Long id) {
        var prestamo = devolverLibroUseCase.devolver(id);
        return PrestamoDTO.desde(prestamo);
    }
}
