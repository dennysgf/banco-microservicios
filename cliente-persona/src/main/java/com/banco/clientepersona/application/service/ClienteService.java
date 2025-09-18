package com.banco.clientepersona.application.service;

import com.banco.clientepersona.application.dto.in.ClienteRequestDto;
import com.banco.clientepersona.application.dto.in.ClienteUpdateRequestDto;
import com.banco.clientepersona.application.dto.out.ClienteResponseDto;

import java.util.List;

public interface ClienteService {
    List<ClienteResponseDto> getAll();
    ClienteResponseDto getById(Long id);
    ClienteResponseDto create(ClienteRequestDto dto);
    ClienteResponseDto update(Long id, ClienteUpdateRequestDto dto);
    void delete(Long id);
}
