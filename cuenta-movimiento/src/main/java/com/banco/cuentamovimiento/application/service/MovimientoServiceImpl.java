package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.application.dto.out.MovimientoResponseDto;
import com.banco.cuentamovimiento.domain.Cuenta;
import com.banco.cuentamovimiento.domain.Movimiento;
import com.banco.cuentamovimiento.domain.exceptions.CuentaNoEncontradaException;
import com.banco.cuentamovimiento.domain.exceptions.SaldoNoDisponibleException;
import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.MovimientoRegistradoEvent;
import com.banco.cuentamovimiento.infrastructure.messaging.producer.MovimientoEventProducer;
import com.banco.cuentamovimiento.infrastructure.repository.CuentaRepository;
import com.banco.cuentamovimiento.infrastructure.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoEventProducer movimientoEventProducer;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository, MovimientoEventProducer movimientoEventProducer) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoEventProducer = movimientoEventProducer;
    }

    @Override
    public MovimientoResponseDto crearMovimiento(MovimientoRequestDto request) {

        Cuenta cuenta = cuentaRepository.findById(request.getCuentaId())
                .orElseThrow(() -> new CuentaNoEncontradaException("cuenta no encontrada"));
        double nuevoSaldo = cuenta.getSaldoInicial() + request.getValor();
        if(nuevoSaldo < 0){
            throw new SaldoNoDisponibleException("Saldo insuficiente");
        }
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = maptoEntity(request);
        movimiento.setCuenta(cuenta);
        movimiento.setSaldo(nuevoSaldo);

        movimiento = movimientoRepository.save(movimiento);
        MovimientoRegistradoEvent event = new MovimientoRegistradoEvent(
                movimiento.getId(),
                cuenta.getId(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getFecha()
        );
        movimientoEventProducer.publishMovimientoRegistrado(event);

        return mapToResponse(movimiento);
    }

    @Override
    public MovimientoResponseDto obtenerMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Movimiento no encontrado"));
        return mapToResponse(movimiento);
    }

    @Override
    public List<MovimientoResponseDto> listarMovimientos() {
        return movimientoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoResponseDto actualizarMovimiento(Long id, MovimientoRequestDto request) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        movimiento.setFecha(request.getFecha());
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setValor(request.getValor());
        movimiento.setSaldo(request.getSaldo());
        movimiento = movimientoRepository.save(movimiento);
        return mapToResponse(movimiento);
    }

    @Override
    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RuntimeException("Movimiento no encontrado");
        }
        movimientoRepository.deleteById(id);
    }

    private Movimiento maptoEntity(MovimientoRequestDto dto){
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dto.getFecha());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(dto.getValor());
        movimiento.setSaldo(dto.getSaldo());
        return movimiento;
    }

    private MovimientoResponseDto mapToResponse(Movimiento movimiento){
        MovimientoResponseDto dto = new MovimientoResponseDto();
        dto.setFecha(movimiento.getFecha().toString());
        dto.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        dto.setTipo(movimiento.getTipoMovimiento());
        dto.setSaldoInicial(movimiento.getCuenta().getSaldoInicial());
        dto.setEstado(movimiento.getCuenta().getEstado());
        dto.setMovimiento(movimiento.getValor());
        dto.setSaldoDisponible(movimiento.getSaldo());
        return dto;
    }
}
