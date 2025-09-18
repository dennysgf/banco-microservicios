package com.banco.cuentamovimiento.domain;

import com.banco.cuentamovimiento.domain.Cuenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String tipoMovimiento;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

}
