package com.example.moneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Lancamento")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name = "data_vencimento")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;

	public Long getCodigo() {
		return Codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Codigo == null) ? 0 : Codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (Codigo == null) {
			if (other.Codigo != null)
				return false;
		} else if (!Codigo.equals(other.Codigo))
			return false;
		return true;
	}
	
	
	
	

}
