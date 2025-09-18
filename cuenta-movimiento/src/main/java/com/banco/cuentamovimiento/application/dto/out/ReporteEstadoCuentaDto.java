package com.banco.cuentamovimiento.application.dto.out;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReporteEstadoCuentaDto {
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
    private String fecha;
}
