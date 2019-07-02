package com.example.moneyapi.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.model.Lancamento_;
import com.example.moneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//criar as restrições
		Predicate[] predicates = criarRestrinções(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestrincoesDePaginacao(query, pageable);	
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}


	private Predicate[] criarRestrinções(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.DESCRICAO))
								, "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento)
					, lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento)
					, lancamentoFilter.getDataVencimentoAte()));
		}
		
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestrincoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber(); //pagina atual
		int totalRegistrosPorPagina = pageable.getPageSize(); //total de páginas
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);		
	} 
	
	private Long total(LancamentoFilter lancamentoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestrinções(lancamentoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
