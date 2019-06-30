package com.example.moneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.moneyapi.model.Categoria;
import com.example.moneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public Categoria editar(Long codigo, Categoria categoria) {
		
		Categoria categoriaSalva = categoriaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		
		return categoriaRepository.save(categoriaSalva);
	}

}
