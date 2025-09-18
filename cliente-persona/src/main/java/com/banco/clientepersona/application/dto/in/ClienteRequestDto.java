package com.banco.clientepersona.application.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequestDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @NotBlank(message = "La identificación es obligatoria")
    private String identificacion;

    private String direccion;
    private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=\\S+$).{4,}$", message = "La contraseña no puede tener espacios y debe tener al menos 4 caracteres")
    private String password;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}
