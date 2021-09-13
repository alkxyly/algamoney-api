package com.algaworks.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemInvalida = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenv = ex.getCause().toString();
		
		var error = new Erro(mensagemInvalida, mensagemDesenv);
		
		return handleExceptionInternal(ex, Arrays.asList(error), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		var errors = criarListaErro(ex.getBindingResult());
		
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaErro(BindingResult bindingResult){
		var erros = new ArrayList<Erro>();
		
		bindingResult.getFieldErrors().forEach(erro ->{
			String mensagemUsuario = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
			String mensagemDevelop = erro.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDevelop));
		});
		
		return erros;
	}
	
	@Data
	@AllArgsConstructor
	public static class Erro{
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
	}

}
