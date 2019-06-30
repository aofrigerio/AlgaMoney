package com.example.moneyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.LancamentoRepository;
import com.example.moneyapi.service.exception.PessoaInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	
	public Lancamento save(Lancamento lancamento) {
		Pessoa pessoa = pessoaService.findById(lancamento.getPessoa().getCodigo());
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

}
