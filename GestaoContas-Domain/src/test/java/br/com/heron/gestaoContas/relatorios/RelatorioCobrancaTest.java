package br.com.heron.gestaoContas.relatorios;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import br.com.heron.gestaoContas.models.BDTest;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class RelatorioCobrancaTest extends BDTest{
	
	private DespesaRecorrente despesa;
	private MesCobranca mes;
	
	private String lineSeparator = System.lineSeparator();

	@Before
	public void setupEnvironment(){
		despesa = new DespesaRecorrente("teste",1);
		despesa.saveIt();
		mes = new MesCobranca(3,2016);
		mes.saveIt();
		
	}
	
	@Test
	public void valorTotal_umaCobranca() {
		despesa.geraCobranca(mes, 21.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		
		assertEquals("Total Cobrancas: 1 ; Valor total: 21.00",relatorio.toString());
	}
	
	@Test
	public void valorTotal_duasCobrancas(){
		MesCobranca mes2 = new MesCobranca(4,2015);
		mes2.saveIt();
		despesa.geraCobranca(mes, 21.00);
		despesa.geraCobranca(mes2, 9.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		
		assertEquals("Total Cobrancas: 2 ; Valor total: 30.00",relatorio.toString());
	}
	
	@Test
	public void getJson_duasCobrancas() throws JsonParseException, JsonMappingException, IOException{
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		despesa.geraCobranca(mes, 21.00);
		despesa.geraCobranca(mes2, 9.00);

		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		
		String jsonRelatorio = relatorio.getJson();
		String expectedJson = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":9.0}],"
				+ "\"total\":30.0}";
		assertEquals(expectedJson,jsonRelatorio);
	}
	
	@Test
	public void getCSV_umaCobranca(){
		despesa.geraCobranca(mes, 21.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		String csv = relatorio.getCSV();
		
		String expected =
				"Cobrança;Mês;Valor"+lineSeparator+
				"teste;3/2016;21.00"+lineSeparator;
		
		assertEquals(expected,csv);		
	}
	
	@Test
	public void getCSV_duasCobranca(){
		despesa.geraCobranca(mes, 21.00);
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		despesa.geraCobranca(mes2, 15.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		String csv = relatorio.getCSV();
		
		String expected =
				"Cobrança;Mês;Valor"+lineSeparator+
				"teste;3/2016;21.00"+lineSeparator+
				"teste;4/2016;15.00"+lineSeparator;
		
		assertEquals(expected,csv);		
	}
	
	@Test
	public void getCSVExport_umaCobranca(){
		despesa.geraCobranca(mes, 21.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		
		RelatorioCobranca relatorio = new RelatorioCobranca(condicao);
		String csv = relatorio.getCSVExport();
		
		String expected =
				"Mês;Despesa"+lineSeparator+
				";teste"+lineSeparator+
				"3/2016;21.00"+lineSeparator;
		
		assertEquals(expected,csv);		
	}


}