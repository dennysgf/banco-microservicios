package com.banco.cuentamovimiento.domain.exceptions;

public class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(String message) {
        super(message);
    }
}
