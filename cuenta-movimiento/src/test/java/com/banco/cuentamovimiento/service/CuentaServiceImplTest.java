package com.banco.cuentamovimiento.service;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.out.CuentaResponseDto;
import com.banco.cuentamovimiento.application.service.CuentaServiceImpl;
import com.banco.cuentamovimiento.domain.Cuenta;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.banco.cuentamovimiento.infrastructure.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

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
    @DisplayName("Creación de una cuenta correctamente")
    void crearCuenta() {
        CuentaRequestDto request = new CuentaRequestDto();
        request.setNumeroCuenta("123456");
        request.setTipoCuenta(TipoCuenta.AHORROS);
        request.setSaldoInicial(1000.0);
        request.setEstado(true);
        request.setClienteId(1L);

        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        CuentaResponseDto response = cuentaService.crearCuenta(request);

        assertNotNull(response);
        assertEquals("123456", response.getNumeroCuenta());
        assertEquals(1000.0, response.getSaldoInicial());
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    @DisplayName("Obtiene una cuenta existente")
    void obtenerCuenta() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        CuentaResponseDto response = cuentaService.obtenerCuenta(1L);

        assertNotNull(response);
        assertEquals("123456", response.getNumeroCuenta());
        assertEquals(1000.0, response.getSaldoInicial());
    }

    @Test
    @DisplayName("Lanza la exceptión si la cuenta no existe")
    void obtenerCuentaNoExiste() {
        when(cuentaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                cuentaService.obtenerCuenta(99L));

        assertEquals("Cuenta no encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Actualiza una cuenta existente")
    void actualizarCuenta() {
        CuentaRequestDto request = new CuentaRequestDto();
        request.setNumeroCuenta("654321");
        request.setTipoCuenta(TipoCuenta.CORRIENTE);
        request.setSaldoInicial(2000.0);
        request.setEstado(false);
        request.setClienteId(2L);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        CuentaResponseDto response = cuentaService.actualizarCuenta(1L, request);

        assertNotNull(response);
        assertEquals("654321", response.getNumeroCuenta());
        assertEquals(2000.0, response.getSaldoInicial());
        assertEquals(false, response.getEstado());
        verify(cuentaRepository).save(any(Cuenta.class));
    }

    @Test
    @DisplayName("Elimina una cuenta existente")
    void eliminarCuenta() {
        when(cuentaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cuentaRepository).deleteById(1L);

        assertDoesNotThrow(() -> cuentaService.eliminarCuenta(1L));
        verify(cuentaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Lanza una excepción al eliminar una cuenta inexistente")
    void eliminarCuentaNoExiste() {
        when(cuentaRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                cuentaService.eliminarCuenta(99L));

        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaRepository, never()).deleteById(99L);
    }
}
