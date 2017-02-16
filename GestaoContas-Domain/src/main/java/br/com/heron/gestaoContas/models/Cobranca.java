package br.com.heron.gestaoContas.models;

import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;

@Table("cobrancas")
@BelongsToParents({ 
	@BelongsTo(foreignKeyName = "despesa_id", parent = DespesaRecorrente.class),
	@BelongsTo(foreignKeyName = "mes_id", parent = MesCobranca.class), 
})
public class Cobranca extends GestaoContasModel {

	public Cobranca() {
	}

	public Cobranca(double valor) {
		setValor(valor);
	}

	private void setValor(double valor) {
		this.set("valor", valor);
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%s - %s R$%.2f", parent(DespesaRecorrente.class).toString(),
				parent(MesCobranca.class).toString(), getDouble("valor"));
	}

	public String toJson(CobrancaJsonHelper... helpers) {
		String finalJson = super.toJson(false);
		for (CobrancaJsonHelper helper : helpers) {
			finalJson = helper.replace(finalJson, this);
		}
		return finalJson;
	}
	
	public String toJsonIncludingAll(){
		return toJson(CobrancaJsonHelper.values());
	}

	public double valor() {
		return getDouble("valor");
	}
	
	public DespesaRecorrente despesa(){
		return parent(DespesaRecorrente.class);
	}
	
	public MesCobranca mesCobranca(){
		return parent(MesCobranca.class);
	}
	
	public String toJsonMaped(){
		Map<String,Object> map = super.toMap();
		map.remove("despesa_id");
		map.remove("mes_id");
		
		try {
			return new ObjectMapper().writeValueAsString(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	public String valorAsString() {
		
		return String.format(Locale.US,"%.2f", valor());
	}

}