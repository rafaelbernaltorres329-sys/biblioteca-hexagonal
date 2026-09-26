package com.biblioteca.infrastructure.config;

import com.biblioteca.domain.ports.out.LibroRepositoryPort;
import com.biblioteca.domain.ports.out.PrestamoRepositoryPort;
import com.biblioteca.domain.ports.out.UsuarioRepositoryPort;
import com.biblioteca.domain.service.LibroService;
import com.biblioteca.domain.service.PrestamoService;
import com.biblioteca.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aqui es donde Spring "conecta" el dominio (puro, sin anotaciones) con
 * las implementaciones concretas de los puertos de salida.
 * Es la unica clase que sabe que el dominio y la infraestructura existen a la vez.
 */
@Configuration
public class BeanConfig {

    @Bean
    public LibroService libroService(LibroRepositoryPort libroRepositoryPort) {
        return new LibroService(libroRepositoryPort);
    }

    @Bean
    public UsuarioService usuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new UsuarioService(usuarioRepositoryPort);
    }

    @Bean
    public PrestamoService prestamoService(PrestamoRepositoryPort prestamoRepositoryPort,
                                            LibroRepositoryPort libroRepositoryPort,
                                            UsuarioRepositoryPort usuarioRepositoryPort) {
        return new PrestamoService(prestamoRepositoryPort, libroRepositoryPort, usuarioRepositoryPort);
    }
}
