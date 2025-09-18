package com.banco.cuentamovimiento.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCuenta {
    AHORROS("Ahorros"),
    CORRIENTE("Corriente");

    private final String valor;

    TipoCuenta(String valor) {
        this.valor = valor;
    }
    @JsonValue
    public String getValor() {
        return valor;
    }
}
