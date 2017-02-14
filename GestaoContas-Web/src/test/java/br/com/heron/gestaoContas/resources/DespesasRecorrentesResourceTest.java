package br.com.heron.gestaoContas.resources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.controllers.DespesaRecorrenteController;

@RunWith(GuiceJunit4Runner.class)
public class DespesasRecorrentesResourceTest extends BDTest {
	
	@Inject
	private DespesasRecorrentesResource service;
	
	@Inject
	private DespesaRecorrenteController controller;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void baseVazia() throws JsonProcessingException, IOException{
		Response response = service.all();
		
		assertEquals(200,response.getStatus());
	}
	
	@Test
	public void basePopulada() throws JsonProcessingException, IOException{
		controller.insert("Teste", 1);
		
		Response response = service.all();
		String stringResponse = response.getEntity().toString();
				
		assertEquals(200,response.getStatus());
		assertEquals("[{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"}]",stringResponse);
	}
	
	@Test
	public void findById() throws JsonProcessingException, IOException{
		controller.insert("Teste", 1);
		
		Response response = service.getByID(1);
		String stringResponse = response.getEntity().toString();
		
		assertEquals(200,response.getStatus());
		assertEquals("{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"}",stringResponse);
	}
	
	@Test
	public void criaDespesa() throws URISyntaxException, JSONException, com.fasterxml.jackson.core.JsonProcessingException, IOException{
		assertEquals(0,controller.findAll().size());

		JsonNode input = mapper.readTree("{\"nome\":\"Teste\",\"dia_de_cobranca\":1}");
		
		Response response = service.create(input);
		assertEquals(201,response.getStatus());
		assertEquals("/despesasRecorrentes/1",response.getHeaderString("Location"));
		assertEquals("{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"}",response.getEntity().toString());
		
		assertEquals(1,controller.findAll().size());
	}

}
