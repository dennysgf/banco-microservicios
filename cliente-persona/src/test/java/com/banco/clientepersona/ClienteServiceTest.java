package com.banco.clientepersona;

import com.banco.clientepersona.application.dto.in.ClienteRequestDto;
import com.banco.clientepersona.application.dto.out.ClienteResponseDto;
import com.banco.clientepersona.application.service.ClienteServiceImpl;
import com.banco.clientepersona.domain.Cliente;
import com.banco.clientepersona.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCliente() {
        ClienteRequestDto request = new ClienteRequestDto();
        request.setNombre("Juan Perez");
        request.setGenero("M");
        request.setEdad(28);
        request.setIdentificacion("9876543210");
        request.setPassword("abcd");
        request.setEstado(true);

        Cliente savedCliente = new Cliente();
        savedCliente.setClienteId(1L);
        savedCliente.setNombre(request.getNombre());
        savedCliente.setGenero(request.getGenero());
        savedCliente.setEdad(request.getEdad());
        savedCliente.setIdentificacion(request.getIdentificacion());
        savedCliente.setPassword(request.getPassword());
        savedCliente.setEstado(request.getEstado());

        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedCliente);

        ClienteResponseDto response = clienteService.create(request);

        assertNotNull(response);
        assertEquals("Juan Perez", response.getNombre());
        assertEquals("9876543210", response.getIdentificacion());
        assertTrue(response.getEstado());
    }

    @Test
    void testGetClienteById() {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("Maria Gomez");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDto response = clienteService.getById(1L);

        assertNotNull(response);
        assertEquals("Maria Gomez", response.getNombre());
    }
}
