package br.com.heron.gestaoContas.relatorios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Joiner;

import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;
import br.com.heron.gestaoContas.relatorios.utils.ExporterHelper;

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
	}
	
	public String getCSVExport(){
		StringBuilder builder = new StringBuilder();
		
		String lineSeparator = System.lineSeparator();
		builder.append("Mês;Despesa"+lineSeparator);
		
		ExporterHelper helper = new ExporterHelper(cobrancas);
			
		builder.append(";"+helper.getDespesas().stream().map(i -> i.nome()).collect(Collectors.joining(";")));
		builder.append(lineSeparator);
		
		
		for (MesCobranca mes : helper.getMeses()) {
			List<String> valores = new LinkedList<>();
			builder.append(mes.toString());
			builder.append(";");
			for (DespesaRecorrente despesa : helper.getDespesas()) {
				valores.add(String.format(Locale.US, "%.2f",helper.getValor(despesa, mes)));
			}
			builder.append(Joiner.on(";").join(valores));
			builder.append(lineSeparator);
		}
		
		return builder.toString();
	}

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