package br.com.heron.gestaoContas.resources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.controllers.MesCobrancaController;

@RunWith(GuiceJunit4Runner.class)
public class MesCobrancaResourceTest extends BDTest{

	@Inject
	private MesCobrancaResource service;
	
	@Inject
	private MesCobrancaController controller;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void baseVazia() throws JsonProcessingException, IOException{
		Response response = service.all();
		
		assertEquals(response.getStatus(),200);
	}
	
	@Test
	public void basePopulada() throws JsonProcessingException, IOException{
		controller.insert(3, 2016);
		 
		Response response = service.all();
		String stringResponse = response.getEntity().toString();
				
		assertEquals(response.getStatus(),200);
		assertEquals("[{\"ano\":2016,\"id\":1,\"mes\":3}]",stringResponse);
	}
	
	@Test
	public void getById() throws JsonProcessingException, IOException{
		controller.insert(3, 2016);
		 
		Response response = service.getById(1);
		String stringResponse = response.getEntity().toString();
				
		assertEquals(response.getStatus(),200);
		assertEquals("{\"ano\":2016,\"id\":1,\"mes\":3}",stringResponse);
	}
	
	@Test
	public void criaMes() throws JsonProcessingException, IOException, URISyntaxException{
		assertEquals(0,controller.findAll().size());
		
		String jsonString = "{\"mes\":3,\"ano\":2016}";
		
		JsonNode input = mapper.readTree(jsonString);
		
		Response response = service.create(input);
		assertEquals(response.getStatus(),201);
		assertEquals("/mesesCobranca/1",response.getHeaderString("Location"));
		
		assertEquals("{\"ano\":2016,\"id\":1,\"mes\":3}",response.getEntity().toString());
		
		assertEquals(1,controller.findAll().size());
	}

}
