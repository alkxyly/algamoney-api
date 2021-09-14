package com.algaworks.algamoney.api.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Embeddable
@Data
public class Endereco {

	@NotBlank
	private String logradouro;
	@NotBlank
	private String numero;
	private String complemento;
	@NotBlank
	private String bairro;
	private String cep;
	@NotBlank
	private String cidade;
	@NotBlank
	private String estado;
}
