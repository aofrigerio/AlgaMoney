package com.example.moneyapi.repository;

import java.util.List;

import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	public List<Pessoa> filtrar(PessoaFilter pessoaFilter);

}
