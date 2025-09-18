package com.banco.clientepersona.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Persona {

	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String genero;
	
	@Column(nullable = false)
	private Integer edad;
	
	@Column(nullable = false, unique = true)
	private String identificacion;
	
	private String direccion;
	
	private String telefono;
}
