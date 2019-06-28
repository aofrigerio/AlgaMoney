package com.example.moneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyapi.event.RecursoCriadoEvent;
import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.repository.LancamentoRepository;

@RestController
@RequestMapping("lancamentos")
public class LancamentoResource {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Lancamento>> localizar(@PathVariable Long codigo) {
		
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		
		if(lancamento.isPresent()) {
			return ResponseEntity.ok(lancamento);
		}else {
			return ResponseEntity.notFound().build(); 
		}
	
	}
	
	@PostMapping
	public ResponseEntity<?> gravar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
				
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

}
