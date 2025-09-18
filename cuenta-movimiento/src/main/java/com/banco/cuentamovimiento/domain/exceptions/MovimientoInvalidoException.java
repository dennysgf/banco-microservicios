package com.banco.cuentamovimiento.domain.exceptions;

public class MovimientoInvalidoException extends RuntimeException {
    public MovimientoInvalidoException(String message) {
        super(message);
    }
}
