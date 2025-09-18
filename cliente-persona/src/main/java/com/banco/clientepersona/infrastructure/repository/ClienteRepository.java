package com.banco.clientepersona.infrastructure.repository;

import com.banco.clientepersona.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByIdentificacion(String identificacion);
}
