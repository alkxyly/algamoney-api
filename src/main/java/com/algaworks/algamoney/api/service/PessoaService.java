package com.algaworks.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Pessoa pessoa,Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo)
				.orElseThrow(() ->  new EmptyResultDataAccessException(1));

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaRepository.save(pessoaSalva);
		return pessoaSalva;
	}
}
