package com.algaworks.algamoney.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "pessoa")
@Data
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Size(min = 3, max = 50)
	private String nome;
	
	@NotNull
	private Boolean ativo;
	
	@Embedded
	private Endereco endereco;

	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return ativo == false;
	}
}
