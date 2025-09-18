package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.out.ReporteEstadoCuentaDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ReporteService {

    List<ReporteEstadoCuentaDto> generarReporte(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
