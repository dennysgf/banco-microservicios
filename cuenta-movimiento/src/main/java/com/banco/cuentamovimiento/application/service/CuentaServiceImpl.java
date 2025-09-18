package com.banco.cuentamovimiento.application.service;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.out.CuentaResponseDto;
import com.banco.cuentamovimiento.domain.Cuenta;
import com.banco.cuentamovimiento.infrastructure.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService{
    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public CuentaResponseDto crearCuenta(CuentaRequestDto request) {
        Cuenta cuenta = maptoEntity(request);
        cuentaRepository.save(cuenta);
        return mapToResponse(cuenta);
    }

    @Override
    public CuentaResponseDto obtenerCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        return mapToResponse(cuenta);
    }

    @Override
    public List<CuentaResponseDto> listarCuentas() {
        return cuentaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaResponseDto actualizarCuenta(Long id, CuentaRequestDto request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado());
        cuenta = cuentaRepository.save(cuenta);
        return mapToResponse(cuenta);
    }

    @Override
    public void eliminarCuenta(Long id) {
        if(!cuentaRepository.existsById(id)){
            throw new RuntimeException("Cuenta no encontrada");
        }
        cuentaRepository.deleteById(id);
    }

    private Cuenta maptoEntity(CuentaRequestDto dto){
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());
        return cuenta;
    }

    private CuentaResponseDto mapToResponse(Cuenta cuenta){
        CuentaResponseDto dto = new CuentaResponseDto();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setEstado(cuenta.getEstado());
        dto.setClienteId(cuenta.getClienteId());
        return dto;

    }
}
