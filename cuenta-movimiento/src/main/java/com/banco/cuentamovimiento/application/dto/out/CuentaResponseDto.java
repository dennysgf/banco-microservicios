package com.banco.cuentamovimiento.application.dto.out;

import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResponseDto {
    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private Long clienteId;
}
