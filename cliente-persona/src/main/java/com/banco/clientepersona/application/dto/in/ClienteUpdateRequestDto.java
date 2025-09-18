package com.banco.clientepersona.application.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateRequestDto {
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
    private String password;
    private Boolean estado;
}
