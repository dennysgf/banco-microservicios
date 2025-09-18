package com.banco.cuentamovimiento.infrastructure.controller;

import com.banco.cuentamovimiento.application.dto.out.ReporteEstadoCuentaDto;
import com.banco.cuentamovimiento.application.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public ResponseEntity<List<ReporteEstadoCuentaDto>> generarReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        List<ReporteEstadoCuentaDto> reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
