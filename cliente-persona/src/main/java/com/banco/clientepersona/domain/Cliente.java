package com.banco.clientepersona.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends Persona{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clienteId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private Boolean estado;
	
}
