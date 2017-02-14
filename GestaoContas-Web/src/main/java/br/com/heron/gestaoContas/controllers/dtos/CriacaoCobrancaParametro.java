package br.com.heron.gestaoContas.controllers.dtos;

import com.fasterxml.jackson.databind.JsonNode;

public class CriacaoCobrancaParametro {
	
	public Long id_despesa;
	public Integer mes;
	public Integer ano;
	public Long id_mes;
	public Double valor;
	
	public CriacaoCobrancaParametro(JsonNode node){
		setDespesa(node);
		setMes(node);
		setValor(node);
	}

	private void setValor(JsonNode node) {
		if(node.get("valor") == null)
			throw new IllegalArgumentException("Obrigatório passar valor");
		valor = node.get("valor").asDouble();
	}

	private void setMes(JsonNode node) {
		if(node.get("id_mes") != null)
			id_mes = node.get("id_mes").longValue();
		if(node.get("mes") != null)
			mes = node.get("mes").intValue();
		if(node.get("ano") != null)
			ano = node.get("ano").intValue();
		
		if(id_mes == null && (ano == null || mes == null))
			throw new IllegalArgumentException("Obrigatório passar mês, por id ou por mes/ano");
	}


	private void setDespesa(JsonNode node) {
		if(node.get("id_despesa") == null)
			throw new IllegalArgumentException("Despesa é obrigatório para gerar cobrança");
		id_despesa = node.get("id_despesa").longValue();
	}

}
