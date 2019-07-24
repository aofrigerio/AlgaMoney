package com.example.moneyapi.repository.pessoa;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyapi.event.RecursoCriadoEvent;
import com.example.moneyapi.model.Categoria;
import com.example.moneyapi.repository.CategoriaRepository;
import com.example.moneyapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@CrossOrigin(maxAge = 10, origins = {"http://localhost:8080"})
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar() {
		return categoriaRepository.findAll(); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);   
		if (categoria.isPresent())
			return ResponseEntity.ok(categoria);
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_EDITAR_CATEGORIA')")
	public ResponseEntity<Categoria> editar(@PathVariable Long codigo, @RequestBody Categoria categoria) {

		Categoria categoriaSalva = categoriaService.editar(codigo, categoria);
		
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA')")
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.deleteById(codigo);
	}

}
