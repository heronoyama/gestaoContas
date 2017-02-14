package br.com.heron.gestaoContas.controllers;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;
import br.com.heron.gestaoContas.relatorios.RelatorioCobranca;
import br.com.heron.gestaoContas.relatorios.RelatorioCobrancaCondicaoBuilder;

@Singleton
public class RelatorioCobrancaController {
	
	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	

	public String getRelatorioJson(RelatorioCobrancaParametro parametro) throws JsonGenerationException, JsonMappingException, IOException {
		RelatorioCobrancaCondicaoBuilder builder = new RelatorioCobrancaCondicaoBuilder();
		
		buildDespesas(parametro.ids_despesas,builder);
		buildMeses(parametro.id_mes_inicio,parametro.id_mes_fim,builder);
		
		RelatorioCobranca relatorio = new RelatorioCobranca(builder.build());
		
		return relatorio.getJson();
	}

	private void buildMeses(Long id_mes_inicio, Long id_mes_fim, RelatorioCobrancaCondicaoBuilder builder) {
		if(id_mes_inicio == null)
			return;
		if(id_mes_fim == null){
			builder.setMes(mesController.findById(id_mes_inicio));
			return;
		}
		builder.setMesInicial(mesController.findById(id_mes_inicio));
		builder.setMesFinal(mesController.findById(id_mes_fim));
	}

	private void buildDespesas(List<Long> ids_despesas, RelatorioCobrancaCondicaoBuilder builder) {
		if(ids_despesas == null)
			return;
		
		for (Long id_despesa : ids_despesas) {
			builder.addDespesa(despesaController.findById(id_despesa));
		}
	}

}