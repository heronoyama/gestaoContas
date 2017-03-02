package br.com.heron.gestaoContas.models;

import org.javalite.activejdbc.annotations.Table;

import com.google.api.client.repackaged.com.google.common.base.Objects;

@Table("despesas_recorrentes")
public class DespesaRecorrente extends GestaoContasModel implements Comparable<DespesaRecorrente>{

	public DespesaRecorrente() {}
	
	public DespesaRecorrente(String nome,int diaDeCobranca) {
		set("nome",nome);
		set("dia_de_cobranca",diaDeCobranca);
	}
	
	@Override
	public String toString() {
		return String.format("%s : Todo dia %d", getString("nome"),getInteger("dia_de_cobranca"));
	}
	
	public String nome(){
		return getString("nome");
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(!(obj instanceof DespesaRecorrente))
			return false;
		
		return nome().equalsIgnoreCase(((DespesaRecorrente)obj).nome());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(nome(),diaDeCobranca());
	}

	private Integer diaDeCobranca() {
		return getInteger("dia_de_cobranca");
	}

	public Cobranca geraCobranca(MesCobranca mesCobranca, double valor) {
		Cobranca cobranca = new Cobranca(valor);
		this.add(cobranca);
		mesCobranca.add(cobranca);
		return cobranca;
	}

	@Override
	public int compareTo(DespesaRecorrente o) {
		return nome().compareTo(o.nome());
	}

	

}
