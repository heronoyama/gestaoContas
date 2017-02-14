package br.com.heron.gestaoContas.controllers;

import java.util.List;

import com.google.inject.Singleton;

import br.com.heron.gestaoContas.models.DespesaRecorrente;

@Singleton
public class DespesaRecorrenteController {
	
	public DespesaRecorrente insert(String nome, int diaDeCobranca){
		DespesaRecorrente despesa = new DespesaRecorrente(nome,diaDeCobranca);
		despesa.saveIt();
		return despesa;
	}

	public List<DespesaRecorrente> findAll() {
		return DespesaRecorrente.findAll();
	}
	
	public DespesaRecorrente findById(long id){
		return DespesaRecorrente.findById(id);
	}
	

}