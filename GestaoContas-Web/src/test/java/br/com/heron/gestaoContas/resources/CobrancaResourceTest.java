package br.com.heron.gestaoContas.resources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.controllers.CobrancaController;
import br.com.heron.gestaoContas.controllers.DespesaRecorrenteController;
import br.com.heron.gestaoContas.controllers.MesCobrancaController;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;

@RunWith(GuiceJunit4Runner.class)
public class CobrancaResourceTest extends BDTest {

	@Inject
	private CobrancaResource service;
	
	@Inject
	private CobrancaController controller;
	
	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	

	@Test
	public void getCobrancas_despesas_duasCobrancas() throws JsonProcessingException, IOException {
		mesController.insert(3, 2016);
		mesController.insert(4, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		controller.geraCobranca(despesa_id, 4, 2016, 5.00);
		
		Response response = service.getCobrancasByDespesa(despesa_id);
		
		assertEquals(200,response.getStatus());
		assertEquals("[{\"despesa_id\":1,\"id\":1,\"mes_id\":1,\"valor\":21.0},{\"despesa_id\":1,\"id\":2,\"mes_id\":2,\"valor\":5.0}]",response.getEntity().toString());
	}
	
	@Test
	public void getCobrancas_mes_duasCobrancas() throws JsonProcessingException, IOException {
		mesController.insert(3, 2016);
		
		DespesaRecorrente despesa1 = despesaController.insert("Teste1", 1);
		DespesaRecorrente despesa2 = despesaController.insert("Teste2", 1);
		
		Long despesa1_id = Long.parseLong(despesa1.getId().toString());
		Long despesa2_id = Long.parseLong(despesa2.getId().toString());
		
		controller.geraCobranca(despesa1_id, 3, 2016, 21.00);
		controller.geraCobranca(despesa2_id, 3, 2016, 5.00);
		
		Response response = service.getCobrancaByMes(3,2016);
		
		assertEquals(200,response.getStatus());
		assertEquals("[{\"despesa_id\":1,\"id\":1,\"mes_id\":1,\"valor\":21.0},{\"despesa_id\":2,\"id\":2,\"mes_id\":1,\"valor\":5.0}]",response.getEntity().toString());
	}
	
	@Test
	public void geraCobranca() throws URISyntaxException, com.fasterxml.jackson.core.JsonProcessingException, IOException{
		
		mesController.insert(3, 2016);
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		String id_despesa = despesa.getId().toString();
		
		List<Cobranca> cobrancas = controller.getCobrancas(Long.parseLong(id_despesa));
		assertEquals(0,cobrancas.size());
		
		String jsonInput = String.format(Locale.US,"{\"id_despesa\":%s,\"mes\":3,\"ano\":2016,\"valor\":%.2f}", id_despesa,21.00);
		JsonNode input = new ObjectMapper().readTree(jsonInput);
		
		Response response = service.geraCobranca(input);
		assertEquals(201,response.getStatus());
		assertEquals("/cobrancas/"+id_despesa,response.getHeaderString("Location"));
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",response.getEntity().toString());
		
		cobrancas = controller.getCobrancas(Long.parseLong(id_despesa));
		assertEquals(1,cobrancas.size());
		
	}
	
	@Test
	public void getCobrancas_helperVazio() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		
		Response response = service.getCobrancasByDespesaHelper(despesa_id,"()");
		
		assertEquals(200,response.getStatus());
		assertEquals("[{\"despesa_id\":1,\"id\":1,\"mes_id\":1,\"valor\":21.0}]",response.getEntity().toString());
	}
	
	@Test
	public void getCobrancas_helperDespesa() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		
		Response response = service.getCobrancasByDespesaHelper(despesa_id,"(despesaRecorrente)");
		
		assertEquals(200,response.getStatus());
		assertEquals("[{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_id\":1,\"valor\":21.0}]",response.getEntity().toString());
	}
	
	@Test
	public void getCobrancas_helperMes() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		
		Response response = service.getCobrancasByDespesaHelper(despesa_id,"(mesCobranca)");
		
		assertEquals(200,response.getStatus());
		assertEquals("[{\"despesa_id\":1,\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}]",response.getEntity().toString());
	}
	
	@Test
	public void getCobrancas_helperMesEDespesa() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		
		String expectedResult = "[{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}]";
		
		Response response = service.getCobrancasByDespesaHelper(despesa_id,"(despesaRecorrente,mesCobranca)");
		assertEquals(200,response.getStatus());
		assertEquals(expectedResult,response.getEntity().toString());
		
		response = service.getCobrancasByDespesaHelper(despesa_id,"(mesCobranca,despesaRecorrente)");
		assertEquals(200,response.getStatus());
		assertEquals(expectedResult,response.getEntity().toString());
	}
	
	@Test
	public void getCobrancas_doisMeses_comHelperDespesa() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		mesController.insert(4, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = despesa.getLongId();
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		controller.geraCobranca(despesa_id, 4, 2016, 5.00);
		
		String expectedResult = "[{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_id\":1,\"valor\":21.0},"
				+ "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":2,\"mes_id\":2,\"valor\":5.0}]";
		
		Response response = service.getCobrancasByDespesaHelper(despesa_id,"(despesaRecorrente)");
		assertEquals(200,response.getStatus());
		assertEquals(expectedResult,response.getEntity().toString());

	}
	
	@Test
	public void getCobranca_ByMes_withHelper() throws JsonProcessingException, IOException{
		mesController.insert(3, 2016);
		mesController.insert(4, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = despesa.getLongId();
		
		controller.geraCobranca(despesa_id, 3, 2016, 21.00);
		controller.geraCobranca(despesa_id, 4, 2016, 5.00);
		
		String expectedResult = "[{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_id\":1,\"valor\":21.0}]";
		
		Response response = service.getCobrancaByMesHelper(3,2016,"(despesaRecorrente)");
		assertEquals(200,response.getStatus());
		assertEquals(expectedResult,response.getEntity().toString());
	}
}
