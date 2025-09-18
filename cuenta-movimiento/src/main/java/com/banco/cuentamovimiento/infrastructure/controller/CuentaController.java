package com.banco.cuentamovimiento.infrastructure.controller;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.out.CuentaResponseDto;
import com.banco.cuentamovimiento.application.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDto> crearCuenta(@RequestBody CuentaRequestDto request) {
        return ResponseEntity.ok(cuentaService.crearCuenta(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> obtenerCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuenta(id));
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponseDto>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDto> actualizarCuenta(
            @PathVariable Long id,
            @RequestBody CuentaRequestDto request) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
