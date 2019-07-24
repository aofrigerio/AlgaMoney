package com.example.moneyapi.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.model.Pessoa_;
import com.example.moneyapi.repository.PessoaRepositoryQuery;
import com.example.moneyapi.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Pessoa> filtrar(PessoaFilter pessoaFilter) {
		
		//Criar uma criteriaBuilder para poder executar o filtro
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);	
		
		//criar as restrições
		//Responsável para filtrar a consulta.
		//Nela passa a pessoaFilter, o builder, e o root.
		Predicate[] predicates = criarRestrincoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	private Predicate[] criarRestrincoes(PessoaFilter pessoaFilter, CriteriaBuilder builder,
			Root<Pessoa> root) {
	
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.NOME))
								, "%" + pessoaFilter.getNome().toLowerCase() + "%"));
		}
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
