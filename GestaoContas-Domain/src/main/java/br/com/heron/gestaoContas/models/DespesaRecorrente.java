package br.com.heron.gestaoContas.models;

import org.javalite.activejdbc.annotations.Table;

@Table("despesas_recorrentes")
public class DespesaRecorrente extends GestaoContasModel{

	public DespesaRecorrente() {}
	
	public DespesaRecorrente(String nome,int diaDeCobranca) {
		set("nome",nome);
		set("dia_de_cobranca",diaDeCobranca);
	}
	
	@Override
	public String toString() {
		return String.format("%s : Todo dia %d", getString("nome"),getInteger("dia_de_cobranca"));
	}

	public Cobranca geraCobranca(MesCobranca mesCobranca, double valor) {
		Cobranca cobranca = new Cobranca(valor);
		this.add(cobranca);
		mesCobranca.add(cobranca);
		return cobranca;
	}

}
