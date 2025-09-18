package com.banco.cuentamovimiento.service;

import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.application.dto.out.MovimientoResponseDto;
import com.banco.cuentamovimiento.application.service.MovimientoServiceImpl;
import com.banco.cuentamovimiento.domain.Cuenta;
import com.banco.cuentamovimiento.domain.Movimiento;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.banco.cuentamovimiento.domain.exceptions.CuentaNoEncontradaException;
import com.banco.cuentamovimiento.domain.exceptions.SaldoNoDisponibleException;
import com.banco.cuentamovimiento.infrastructure.repository.CuentaRepository;
import com.banco.cuentamovimiento.infrastructure.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovimientoServiceImplTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("123456");
        cuenta.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);
    }

    @Test
    @DisplayName("Debe registrar un depósito y aumentar el saldo")
    void deposito() {
        MovimientoRequestDto request = new MovimientoRequestDto();
        request.setCuentaId(1L);
        request.setFecha(LocalDateTime.now());
        request.setTipoMovimiento("DEPOSITO");
        request.setValor(500.0);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));

        MovimientoResponseDto response = movimientoService.crearMovimiento(request);

        assertNotNull(response);
        assertEquals(1500.0, response.getSaldoDisponible());
        verify(cuentaRepository).save(any(Cuenta.class));
    }

    @Test
    @DisplayName("Debe registrar un retiro y disminuir el saldo")
    void retiro() {
        MovimientoRequestDto request = new MovimientoRequestDto();
        request.setCuentaId(1L);
        request.setFecha(LocalDateTime.now());
        request.setTipoMovimiento("RETIRO");
        request.setValor(-300.0);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));

        MovimientoResponseDto response = movimientoService.crearMovimiento(request);

        assertNotNull(response);
        assertEquals(700.0, response.getSaldoDisponible());
        verify(cuentaRepository).save(any(Cuenta.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si no hay saldo suficiente")
    void saldoInsuficiente() {
        MovimientoRequestDto request = new MovimientoRequestDto();
        request.setCuentaId(1L);
        request.setFecha(LocalDateTime.now());
        request.setTipoMovimiento("RETIRO");
        request.setValor(-2000.0);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        assertThrows(SaldoNoDisponibleException.class, () -> movimientoService.crearMovimiento(request));
        verify(cuentaRepository, never()).save(any(Cuenta.class));
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si la cuenta no existe")
    void cuentaNoExiste() {
        MovimientoRequestDto request = new MovimientoRequestDto();
        request.setCuentaId(99L);
        request.setFecha(LocalDateTime.now());
        request.setTipoMovimiento("DEPOSITO");
        request.setValor(100.0);

        when(cuentaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CuentaNoEncontradaException.class, () -> movimientoService.crearMovimiento(request));
    }
}
