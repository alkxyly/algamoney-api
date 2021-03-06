package com.algaworks.algamoney.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo())
								     .orElseThrow(() -> new PessoaInexistenteOuInativaException());
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}

	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo)
							.orElseThrow(() -> new PessoaInexistenteOuInativaException());
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo;
	}
}
