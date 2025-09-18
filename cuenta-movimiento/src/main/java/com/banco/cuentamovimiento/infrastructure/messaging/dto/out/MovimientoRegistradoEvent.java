package com.banco.cuentamovimiento.infrastructure.messaging.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRegistradoEvent {
    private Long id;
    private Long cuentaId;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    private LocalDateTime fecha;
}
