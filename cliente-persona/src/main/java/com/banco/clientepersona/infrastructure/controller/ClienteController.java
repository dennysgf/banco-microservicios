package com.banco.clientepersona.infrastructure.controller;

import com.banco.clientepersona.application.dto.in.ClienteRequestDto;
import com.banco.clientepersona.application.dto.in.ClienteUpdateRequestDto;
import com.banco.clientepersona.application.dto.out.ClienteResponseDto;
import com.banco.clientepersona.application.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>>getAll(){
        return ResponseEntity.ok(clienteService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        ClienteResponseDto response = clienteService.getById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@Valid @RequestBody ClienteRequestDto dto) {
        return ResponseEntity.ok(clienteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateRequestDto dto) {
        ClienteResponseDto response = clienteService.update(id, dto);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
