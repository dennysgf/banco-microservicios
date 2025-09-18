package com.banco.cuentamovimiento.application.dto.in;

import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaRequestDto {
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private Long clienteId;
}
