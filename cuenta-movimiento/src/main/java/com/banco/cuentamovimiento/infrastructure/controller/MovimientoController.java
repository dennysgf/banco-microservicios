package com.banco.cuentamovimiento.infrastructure.controller;

import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.application.dto.out.MovimientoResponseDto;
import com.banco.cuentamovimiento.application.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponseDto> crearMovimiento(@RequestBody MovimientoRequestDto request) {
        return ResponseEntity.ok(movimientoService.crearMovimiento(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDto> obtenerMovimiento(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerMovimiento(id));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponseDto>> listarMovimientos() {
        return ResponseEntity.ok(movimientoService.listarMovimientos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDto> actualizarMovimiento(
            @PathVariable Long id,
            @RequestBody MovimientoRequestDto request) {
        return ResponseEntity.ok(movimientoService.actualizarMovimiento(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
