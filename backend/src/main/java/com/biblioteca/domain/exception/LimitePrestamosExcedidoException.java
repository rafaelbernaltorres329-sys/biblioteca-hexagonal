package com.biblioteca.domain.exception;

public class LimitePrestamosExcedidoException extends RuntimeException {
    public LimitePrestamosExcedidoException(String mensaje) {
        super(mensaje);
    }
}
