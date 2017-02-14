package br.com.heron.gestaoContas.controllers;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.com.heron.gestaoContas.controllers.dtos.CriacaoCobrancaParametro;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

@Singleton
public class CobrancaController {

	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	
	public Cobranca geraCobranca(long id_despesa, int mes, int ano, double valor){
		DespesaRecorrente despesa = despesaController.findById(id_despesa);
		MesCobranca mesCobranca = mesController.findOrCreate(mes, ano);
		return despesa.geraCobranca(mesCobranca, valor);
	}
	
	public List<Cobranca> getCobrancas(long despesa_id){
		DespesaRecorrente despesa = despesaController.findById(despesa_id);
		return despesa.getAll(Cobranca.class);
	}

	public List<Cobranca> getCobrancas(int mes, int ano) {
		MesCobranca mesCobranca = mesController.find(mes, ano);
		return mesCobranca.getAll(Cobranca.class);
	}

	public Cobranca geraCobranca(CriacaoCobrancaParametro parametro) {
		DespesaRecorrente despesa = despesaController.findById(parametro.id_despesa);
		MesCobranca mesCobranca = parametro.id_mes == null ? mesController.findOrCreate(parametro.mes, parametro.ano) : mesController.findById(parametro.id_mes);
		
		return despesa.geraCobranca(mesCobranca, parametro.valor);
	}
	
}
