package com.example.moneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Compartilhar para todos os controllers
@ControllerAdvice
//Classe para tratar exceções
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
		
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		String messagemDesenvolvedor = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, messagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado",null, LocaleContextHolder.getLocale());
		String messagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, messagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handlerConstraintViolationException(DataIntegrityViolationException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida",null, LocaleContextHolder.getLocale());
		String messagemDesenvolvedor = org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, messagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
				
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
		
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return erros;
	}
	
	public static class Erro {
		private String messagemUsuario;
		private String messagemDesenvolvedor;
		
		
		public String getMessagemUsuario() {
			return messagemUsuario;
		}
		
		
		public Erro(String messagemUsuario, String messagemDesenvolvedor) {
			super();
			this.messagemUsuario = messagemUsuario;
			this.messagemDesenvolvedor = messagemDesenvolvedor;
		}


		public void setMessagemUsuario(String messagemUsuario) {
			this.messagemUsuario = messagemUsuario;
		}
		public String getMessagemDesenvolvedor() {
			return messagemDesenvolvedor;
		}
		public void setMessagemDesenvolvedor(String messagemDesenvolvedor) {
			this.messagemDesenvolvedor = messagemDesenvolvedor;
		}
		
		
	}
	
}
