package com.example.moneyapi.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyapi.event.RecursoCriadoEvent;
import com.example.moneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.repository.LancamentoRepository;
import com.example.moneyapi.repository.filter.LancamentoFilter;
import com.example.moneyapi.service.LancamentoService;
import com.example.moneyapi.service.exception.PessoaInativaException;

@RestController
@RequestMapping("lancamentos")
public class LancamentoResource {
	
	@Autowired
	LancamentoRepository lancamentoRepository;
	
	@Autowired
	LancamentoService lancamentoService;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@GetMapping
	public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
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
				
		Lancamento lancamentoSalvo = lancamentoService.save(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@ExceptionHandler({PessoaInativaException.class})
	public ResponseEntity<Object> handlePessoainativaException (PessoaInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inativa",null, LocaleContextHolder.getLocale());
		String messagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, messagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}

}
