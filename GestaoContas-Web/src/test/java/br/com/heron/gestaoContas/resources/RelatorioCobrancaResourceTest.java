package br.com.heron.gestaoContas.resources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.controllers.CobrancaController;
import br.com.heron.gestaoContas.controllers.DespesaRecorrenteController;
import br.com.heron.gestaoContas.controllers.MesCobrancaController;

@RunWith(GuiceJunit4Runner.class)
public class RelatorioCobrancaResourceTest extends BDTest{
	
	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	
	@Inject
	private CobrancaController cobrancaController;
	
	@Inject
	private RelatorioCobrancaResource subject;
	
	@Before
	public void setupEnvironment(){
		despesaController.insert("Teste", 1);
		despesaController.insert("DespesaExtra", 2);
		despesaController.insert("NovaDespesaExtra",3);
		
		mesController.insert(3, 2016); //mes1
		mesController.insert(4, 2016); //mes2
		mesController.insert(5, 2017); //mes3
		mesController.insert(5, 2018); //mes4
		
		cobrancaController.geraCobranca(1, 3, 2016, 10.00);
		cobrancaController.geraCobranca(2, 3, 2016, 15.00);
		cobrancaController.geraCobranca(1, 4, 2016, 20.00);
		cobrancaController.geraCobranca(2, 4, 2016, 25.00);
		cobrancaController.geraCobranca(2, 5, 2016, 30.00); //mes5
		cobrancaController.geraCobranca(3, 5, 2017, 35.00);
		cobrancaController.geraCobranca(3, 5, 2018, 40.00);
		cobrancaController.geraCobranca(1, 5, 2018, 45.00);
	}

	@Test
	public void relatorioDespesas_umaDespesa() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasDasDespesas("(1)");
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":3,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":20.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":8,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":45.0}"
				+ "],"
				+ "\"total\":75.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_duasDespesas() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasDasDespesas("(1,2)");
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":3,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":20.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":4,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":25.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":5,\"mes_cobranca\":{\"ano\":2016,\"id\":5,\"mes\":5},\"valor\":30.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":8,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":45.0}"
				+ "],"
				+ "\"total\":145.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_duasDespesas_from_to() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasDasDespesasFromTo("(1,2)",1,2);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":3,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":20.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":4,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":25.0}"
				+ "],"
				+ "\"total\":70.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_mesInicio_mesFim() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasFromTo(1,5);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":3,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":20.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":4,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":25.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":5,\"mes_cobranca\":{\"ano\":2016,\"id\":5,\"mes\":5},\"valor\":30.0}"
				+ "],"
				+ "\"total\":100.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_somenteMes() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasOfMes(1);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0}"
				+ "],"
				+ "\"total\":25.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_umaDespesa_somenteUmMes() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasDasDespesasOfMes("(1)",1);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0}"
				+ "],"
				+ "\"total\":10.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorioDespesas_duasDespesas_somenteUmMes() throws JsonGenerationException, JsonMappingException, IOException {
		Response response = subject.cobrancasDasDespesasOfMes("(1,2)",5);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":5,\"mes_cobranca\":{\"ano\":2016,\"id\":5,\"mes\":5},\"valor\":30.0}"
				+ "],"
				+ "\"total\":30.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_despesas_from() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasDespesasFrom("(3)", 3);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":6,\"mes_cobranca\":{\"ano\":2017,\"id\":3,\"mes\":5},\"valor\":35.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":7,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":40.0}"
				+ "],\"total\":75.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_despesas_to() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasDasDespesasTo("(3)", 4);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":6,\"mes_cobranca\":{\"ano\":2017,\"id\":3,\"mes\":5},\"valor\":35.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":7,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":40.0}"
				+ "],\"total\":75.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_cobrancas_from() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasFrom(3);

		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":6,\"mes_cobranca\":{\"ano\":2017,\"id\":3,\"mes\":5},\"valor\":35.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":3,\"id\":3,\"nome\":\"NovaDespesaExtra\"},\"id\":7,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":40.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":8,\"mes_cobranca\":{\"ano\":2018,\"id\":4,\"mes\":5},\"valor\":45.0}"
				+ "],\"total\":120.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_cobrancas_to() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasTo(2);

	
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":["
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":3,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":20.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":4,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":25.0}"
				+ "],\"total\":70.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_cobrancas_doMesEAno() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasDoMesAno(3,2016);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "{\"cobrancas\":"
				+ "["
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":10.0},"
					+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":2,\"id\":2,\"nome\":\"DespesaExtra\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":15.0}"
				+ "],"
				+ "\"total\":25.0}";
		
		assertEquals(jsonExpected,jsonResponse);
	}
	
	@Test
	public void relatorio_cobrancas_doMesEAno_semCobrancas() throws JsonGenerationException, JsonMappingException, IOException{
		Response response = subject.cobrancasDoMesAno(6,2018);
		
		assertEquals(200,response.getStatus());
		
		String jsonResponse = response.getEntity().toString();
		String jsonExpected = "[]";
		
		assertEquals(jsonExpected,jsonResponse);
	}

}