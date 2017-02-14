package br.com.heron.gestaoContas.relatorios;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class RelatorioCobrancaCondicaoBuilder {
	
	private MesCobranca mesInicial;
	private MesCobranca mesFinal;
	private Set<DespesaRecorrente> despesas = new HashSet<>();
	
	public RelatorioCobrancaCondicaoBuilder setMesInicial(MesCobranca mes){
		this.mesInicial = mes;
		return this;
	}
	
	public RelatorioCobrancaCondicaoBuilder setMesFinal(MesCobranca mes){
		this.mesFinal = mes;
		return this;
	}
	
	public RelatorioCobrancaCondicaoBuilder setMes(MesCobranca mes){
		this.mesInicial = mes;
		this.mesFinal = mes;
		return this;
	}
	
	public RelatorioCobrancaCondicaoBuilder addDespesa(DespesaRecorrente despesa){
		this.despesas.add(despesa);
		return this;
	}
	
	public RelatorioCobrancaCondicaoBuilder addDespesas(List<DespesaRecorrente> despesas){
		this.despesas.addAll(despesas);
		return this;
	}
	
	public RelatorioCobrancaCondicao build(){
		if(mesInicial != null && mesFinal == null)
			mesFinal = mesInicial;
		validate();
		return new RelatorioCobrancaCondicao(mesInicial,mesFinal,despesas);
	}
	
	private void validate(){
		if(this.mesInicial == null && this.mesFinal == null)
			return;
		if(this.mesInicial.equals(this.mesFinal))
			return;
		if(this.mesInicial.after(this.mesFinal))
			throw new IllegalArgumentException("Mes final deve ser antes ou igual ao Mes Inicial");
			
	}
	

}
