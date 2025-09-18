package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.out.CuentaResponseDto;

import java.util.List;

public interface CuentaService {
    CuentaResponseDto crearCuenta(CuentaRequestDto request);
    CuentaResponseDto obtenerCuenta(Long id);
    List<CuentaResponseDto> listarCuentas();
    CuentaResponseDto actualizarCuenta(Long id, CuentaRequestDto request);
    void eliminarCuenta(Long id);
}
