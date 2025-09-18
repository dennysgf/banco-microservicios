package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.out.ClienteResponseDto;
import com.banco.cuentamovimiento.application.dto.out.MovimientoResponseDto;
import com.banco.cuentamovimiento.application.dto.out.ReporteEstadoCuentaDto;
import com.banco.cuentamovimiento.domain.Cuenta;
import com.banco.cuentamovimiento.domain.Movimiento;
import com.banco.cuentamovimiento.infrastructure.repository.CuentaRepository;
import com.banco.cuentamovimiento.infrastructure.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final RestTemplate restTemplate;

    @Value("${clientes.service.url}")
    private String clientesServiceUrl;

    public ReporteServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository, RestTemplate restTemplate) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<ReporteEstadoCuentaDto> generarReporte(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ClienteResponseDto cliente = restTemplate.getForObject(
                clientesServiceUrl + "/" + clienteId,
                ClienteResponseDto.class
        );

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return cuentas.stream()
                .flatMap(cuenta -> {
                    List<Movimiento> movimientos = movimientoRepository.findMovimientosByCuentaAndFecha(
                            cuenta.getId(), fechaInicio, fechaFin);

                    return movimientos.stream().map(mov -> {
                        ReporteEstadoCuentaDto dto = new ReporteEstadoCuentaDto();
                        dto.setFecha(mov.getFecha().format(formatter));
                        dto.setCliente(cliente != null ? cliente.getNombre() : "Desconocido");
                        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
                        dto.setTipo(cuenta.getTipoCuenta().getValor());
                        dto.setSaldoInicial(cuenta.getSaldoInicial());
                        dto.setEstado(cuenta.getEstado());
                        dto.setMovimiento(mov.getValor());
                        dto.setSaldoDisponible(mov.getSaldo());
                        return dto;
                    });
                })
                .collect(Collectors.toList());
    }
}
