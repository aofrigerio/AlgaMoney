package com.example.moneyapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.filter.PessoaFilter;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{
	
}
