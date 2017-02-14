package br.com.heron.gestaoContas.models;

import java.util.ArrayList;
import java.util.List;

import org.javalite.activejdbc.Model;

import com.google.common.base.Joiner;

public enum CobrancaJsonHelper {
	DESPESA_RECORRENTE("despesaRecorrente","\"despesa_recorrente\"","\"despesa_id\":%d") {
		@Override
		public Model getModel(Cobranca cobranca) {
			return cobranca.parent(DespesaRecorrente.class);
		}

	
	
	},  
	MES_COBRANCA("mesCobranca","\"mes_cobranca\"","\"mes_id\":%d") {
		@Override
		public Model getModel(Cobranca cobranca) {
			return cobranca.parent(MesCobranca.class);
		}

	};
	
	private String nome;
	private String entityName;
	private String snippet;

	private CobrancaJsonHelper(String nome,String entityName, String snippet) {
		this.nome = nome;
		this.entityName = entityName;
		this.snippet = snippet;
	}
	
	public String getEntityName(){
		return entityName;
	}
	
	public abstract Model getModel(Cobranca cobranca);
	
	public static String listaPossibilidades(){
		String listaFinal = "{%s}";
		
		List<String> valores = new ArrayList<>();
		for (CobrancaJsonHelper filter: CobrancaJsonHelper.values()) {
			valores.add(filter.nome);
		}
		
		return String.format(listaFinal, Joiner.on(",").join(valores));
	}

	public String replace(String finalJson, Cobranca cobranca) {
		Model modelToReplace = getModel(cobranca);
		String toReplace = String.format(snippet, modelToReplace.getLongId());
		return finalJson.replaceAll(toReplace, entityName+":"+modelToReplace.toJson(false));
		
	}
	
	public static CobrancaJsonHelper get(String filter) {
		for (CobrancaJsonHelper option: CobrancaJsonHelper.values()) {
			if(option.nome.equalsIgnoreCase(filter))
				return option;
		}
		return null;
	}
	
	public static String allHelpers(){
		List<String> valores = new ArrayList<>();
		for (CobrancaJsonHelper helper : CobrancaJsonHelper.values()) {
			valores.add(helper.nome);
		}
		return "("+Joiner.on(",").join(valores)+")";
	}
	
}