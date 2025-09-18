package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.application.dto.out.MovimientoResponseDto;

import java.util.List;

public interface MovimientoService {
    MovimientoResponseDto crearMovimiento(MovimientoRequestDto request);
    MovimientoResponseDto obtenerMovimiento(Long id);
    List<MovimientoResponseDto> listarMovimientos();
    MovimientoResponseDto actualizarMovimiento(Long id, MovimientoRequestDto request);
    void eliminarMovimiento(Long id);
}
