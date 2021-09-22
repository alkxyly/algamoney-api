package com.algaworks.algamoney.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento buscarLancamentoPorCodigo(Long codigo) {
		return lancamentoRepository.findById(codigo)
				.orElseThrow(() ->  new EmptyResultDataAccessException(1));
	}


	public Lancamento salvar(@Valid Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if(!pessoa.isPresent() || pessoa.get().isInativo())
			throw new PessoaInexistenteOuInativaException();
			
		return lancamentoRepository.save(lancamento);
	}
}
