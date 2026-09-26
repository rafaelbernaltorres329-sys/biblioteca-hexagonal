package com.biblioteca.infrastructure.config;

import com.biblioteca.domain.ports.in.RegistrarLibroUseCase;
import com.biblioteca.domain.ports.in.RegistrarUsuarioUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Carga datos de ejemplo al arrancar, para poder probar la app inmediatamente. */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RegistrarLibroUseCase registrarLibroUseCase;
    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public DataInitializer(RegistrarLibroUseCase registrarLibroUseCase,
                            RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarLibroUseCase = registrarLibroUseCase;
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }

    @Override
    public void run(String... args) {
        registrarLibroUseCase.registrar("Cien años de soledad", "Gabriel García Márquez", "978-0307474728");
        registrarLibroUseCase.registrar("Clean Code", "Robert C. Martin", "978-0132350884");
        registrarLibroUseCase.registrar("El Quijote", "Miguel de Cervantes", "978-8420412146");
        registrarLibroUseCase.registrar("Refactoring", "Martin Fowler", "978-0134757599");

        registrarUsuarioUseCase.registrar("Rafael Bernal", "rafael@example.com");
        registrarUsuarioUseCase.registrar("Ana Torres", "ana@example.com");
    }
}
