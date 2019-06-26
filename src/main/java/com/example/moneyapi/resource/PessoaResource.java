package com.example.moneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Pessoa>> buscarPeloCodigo(@PathVariable Long id){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		
		if (pessoa.isPresent())
			return ResponseEntity.ok(pessoa);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> gravar(@RequestBody Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getId()).toUri();
		
		return ResponseEntity.created(uri).body(pessoaSalva);
	}

}
