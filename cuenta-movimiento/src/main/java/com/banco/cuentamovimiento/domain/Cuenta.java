package com.banco.cuentamovimiento.domain;

import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(nullable = false)
    private Double saldoInicial;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;


    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos = new ArrayList<>();
}
