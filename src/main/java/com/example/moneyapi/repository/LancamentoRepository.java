package com.example.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
