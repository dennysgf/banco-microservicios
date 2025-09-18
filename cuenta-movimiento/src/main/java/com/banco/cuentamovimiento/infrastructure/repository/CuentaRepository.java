package com.banco.cuentamovimiento.infrastructure.repository;

import com.banco.cuentamovimiento.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByClienteId(Long clienteId);
}
