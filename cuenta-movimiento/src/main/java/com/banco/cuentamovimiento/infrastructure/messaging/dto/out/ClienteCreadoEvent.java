package com.banco.cuentamovimiento.infrastructure.messaging.dto.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCreadoEvent {
    private Long id;
    private String nombre;
}
