package com.banco.cuentamovimiento.application.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoRequestDto {

    private Long cuentaId;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

}
