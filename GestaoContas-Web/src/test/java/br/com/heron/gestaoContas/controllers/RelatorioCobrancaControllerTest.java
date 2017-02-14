package br.com.heron.gestaoContas.controllers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;

@RunWith(GuiceJunit4Runner.class)
public class RelatorioCobrancaControllerTest extends BDTest{

	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	
	@Inject
	private CobrancaController cobrancaController;
	
	@Inject
	private RelatorioCobrancaController subject;
	
	@Test
	public void sanityCheck_jsonDTO() throws JsonProcessingException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.ids_despesas = Arrays.asList(1l);
		parametro.id_mes_inicio = 1l;
		parametro.id_mes_fim = 2l;
		
		ObjectMapper mapper = new ObjectMapper();
		assertEquals("{\"ids_despesas\":[1],\"id_mes_inicio\":1,\"id_mes_fim\":2}",mapper.writeValueAsString(parametro));
	}
	
	@Test
	public void getCobrancas_duasDespesasCadastradas_buscaUmaDespesa() throws JsonGenerationException, JsonMappingException, IOException {
		despesaController.insert("teste", 1);

		mesController.insert(3, 2016);
		cobrancaController.geraCobranca(1,3,2016,21.00);
		
		mesController.insert(4, 2016);
		cobrancaController.geraCobranca(1,4,2016,9.00);
		
		despesaController.insert("Despesa Extra", 1);
		cobrancaController.geraCobranca(2,3,2016,10.00);
		cobrancaController.geraCobranca(2,4,2016,100.00);

		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.ids_despesas = Arrays.asList(1l);
		parametro.id_mes_inicio = 1l;
		parametro.id_mes_fim = 2l;
		
		String jsonRelatorio = subject.getRelatorioJson(parametro);
		String expectedJson = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":9.0}],"
				+ "\"total\":30.0}";
		assertEquals(expectedJson,jsonRelatorio);
		
	}

}