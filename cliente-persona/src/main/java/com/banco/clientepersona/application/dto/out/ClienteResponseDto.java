package com.banco.clientepersona.application.dto.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDto {

    private Long clienteId;
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private Boolean estado;
    
}
