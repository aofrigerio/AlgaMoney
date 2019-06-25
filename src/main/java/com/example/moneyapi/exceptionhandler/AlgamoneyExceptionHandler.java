package com.example.moneyapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
		
		//ex, erros do handler
		// header, cabeçalho da requisição
		//request, requisição
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		String messagemDesenvolvedor = ex.getCause().toString();
		
		return handleExceptionInternal(ex, new Erro(mensagemUsuario, messagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
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
