package com.biblioteca.domain.model;

import java.util.Objects;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;

    public Usuario(Long id, String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email del usuario no es valido");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public static Usuario nuevo(String nombre, String email) {
        return new Usuario(null, nombre, email);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
