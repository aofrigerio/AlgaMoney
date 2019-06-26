package com.example.moneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.moneyapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener {
	
	@EventListener
	public void handleResponseLocationHeader(RecursoCriadoEvent event ) {
		HttpServletResponse response = event.getResponse();
		Long codigoRecurso = event.getCodigo();
		
		URI uri = adicionarHeaderLocation(codigoRecurso);
		
		response.addHeader("Location", uri.toASCIIString());
				
	}

	private URI adicionarHeaderLocation(Long codigoRecurso) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}")
				.buildAndExpand(codigoRecurso)
				.toUri();
		return uri;
	}
	

}
