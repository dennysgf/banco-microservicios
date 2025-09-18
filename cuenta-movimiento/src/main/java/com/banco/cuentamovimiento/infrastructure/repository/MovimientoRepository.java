package com.banco.cuentamovimiento.infrastructure.repository;

import com.banco.cuentamovimiento.domain.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m " +
            "WHERE m.cuenta.id = :cuentaId " +
            "AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findMovimientosByCuentaAndFecha(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
