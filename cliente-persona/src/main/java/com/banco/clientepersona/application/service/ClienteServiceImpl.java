package com.banco.clientepersona.application.service;

import com.banco.clientepersona.application.dto.in.ClienteRequestDto;
import com.banco.clientepersona.application.dto.in.ClienteUpdateRequestDto;
import com.banco.clientepersona.application.dto.out.ClienteResponseDto;
import com.banco.clientepersona.domain.Cliente;
import com.banco.clientepersona.domain.exceptions.IdentificacionDuplicadaException;
import com.banco.clientepersona.infrastructure.messaging.ClienteActualizadoEvent;
import com.banco.clientepersona.infrastructure.messaging.ClienteCreadoEvent;
import com.banco.clientepersona.infrastructure.messaging.producer.ClienteEventProducer;
import com.banco.clientepersona.infrastructure.repository.ClienteRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl  implements ClienteService{

    private final ClienteRepository clienteRepository;
    private final ClienteEventProducer clienteEventProducer;
    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteEventProducer clienteEventProducer) {
        this.clienteRepository = clienteRepository;
        this.clienteEventProducer = clienteEventProducer;
    }

    @Override
    public List<ClienteResponseDto> getAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDto getById(Long id) {
        return clienteRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @Override
    public ClienteResponseDto create(ClienteRequestDto dto) {
        Cliente cliente = mapToEntity(dto);
        Cliente saved = clienteRepository.save(cliente);

        ClienteCreadoEvent event = new ClienteCreadoEvent(
                saved.getClienteId(),
                saved.getNombre(),
                saved.getGenero(),
                saved.getEdad(),
                saved.getIdentificacion(),
                saved.getDireccion(),
                saved.getTelefono(),
                saved.getEstado()
        );

        clienteEventProducer.publishClienteCreado(event);
        return mapToResponse(saved);
    }

    @Override
    public ClienteResponseDto update(Long id, ClienteUpdateRequestDto dto) {
        Cliente existing = clienteRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setNombre(dto.getNombre());
        existing.setGenero(dto.getGenero());
        existing.setEdad(dto.getEdad());
        existing.setDireccion(dto.getDireccion());
        existing.setTelefono(dto.getTelefono());
        existing.setPassword(dto.getPassword());
        existing.setEstado(dto.getEstado());

        Cliente saved = clienteRepository.save(existing);

        ClienteActualizadoEvent event = new ClienteActualizadoEvent(
                saved.getClienteId(),
                saved.getNombre(),
                saved.getGenero(),
                saved.getEdad(),
                saved.getIdentificacion(),
                saved.getDireccion(),
                saved.getTelefono(),
                saved.getEstado()
        );

        clienteEventProducer.publishClienteActualizado(event);

        return mapToResponse(saved);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    private Cliente mapToEntity(ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setPassword(dto.getPassword());
        cliente.setEstado(dto.getEstado());
        return cliente;
    }
    private ClienteResponseDto mapToResponse(Cliente cliente){
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEstado(cliente.getEstado());
        return dto;
    }
}
