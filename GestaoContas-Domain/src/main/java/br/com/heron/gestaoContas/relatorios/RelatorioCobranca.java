package br.com.heron.gestaoContas.relatorios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.heron.gestaoContas.models.Cobranca;

public class RelatorioCobranca {
	
	private List<Cobranca> cobrancas;
	private final double total;

	public RelatorioCobranca(RelatorioCobrancaCondicao condicao) {
		this.cobrancas = condicao.getCobrancas();
		this.total = calculaTotal();
	}

	private double calculaTotal() {
		double valor = 0.0;
		for (Cobranca cobranca : cobrancas) {
			valor += cobranca.valor();
		}
		return valor;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US,"Total Cobrancas: %d ; Valor total: %.2f", cobrancas.size(),total);
	}

	public String getJson() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		Map<String,Object> outerMap = new TreeMap<>();
		outerMap.put("total", total);
		
		List<Map<String, Object>> cobrancasMap = getCobrancasMap();
		outerMap.put("cobrancas", cobrancasMap);
		
		return mapper.writeValueAsString(outerMap);
	}
	
	public String getCSV(){
		StringBuilder builder = new StringBuilder();
		builder.append("Cobrança;Mês;Valor"+System.lineSeparator());
		
		for (Cobranca cobranca : cobrancas) {
			builder.append(cobranca.despesa().nome()+";"+cobranca.mesCobranca()+";"+cobranca.valorAsString()+System.lineSeparator());
		}
		
		return builder.toString();
	};

	private List<Map<String, Object>> getCobrancasMap() {
		List<Map<String,Object>> cobrancasMap = new ArrayList<>();
		for (Cobranca cobranca : cobrancas) {
			Map<String, Object> cobrancaMap = cobranca.toMap();
			cobrancaMap.remove("despesa_id");
			cobrancaMap.remove("mes_id");
			cobrancasMap.add(cobrancaMap);
		}
		return cobrancasMap;
	}

}