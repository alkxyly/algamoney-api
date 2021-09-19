package com.algaworks.algamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	
	public Lancamento buscarLancamentoPorCodigo(Long codigo) {
		return lancamentoRepository.findById(codigo)
				.orElseThrow(() ->  new EmptyResultDataAccessException(1));
	}
}
