package br.com.heron.gestaoContas.resources;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.models.Usuario;

@RunWith(GuiceJunit4Runner.class)
public class AutenticacaoResourceTest extends BDTest{
	
	@Inject
	private AutenticacaoResource service;
	
	@Test
	public void autenticaOK() throws JsonProcessingException, IOException{
		new Usuario("Heron","admin","email@email.com").saveIt();
		
		String jsonInput = String.format(Locale.US,"{\"email\":\"%s\",\"senha\":\"%s\"}", "email@email.com","admin");
		JsonNode input = new ObjectMapper().readTree(jsonInput);
		
		Response response = service.login(input);
		
		assertEquals(200,response.getStatus());
		
	}
	
	@Test
	public void naoAutentica() throws JsonProcessingException, IOException{
		String jsonInput = String.format(Locale.US,"{\"email\":\"%s\",\"senha\":\"%s\"}", "email@email.com","admin");
		JsonNode input = new ObjectMapper().readTree(jsonInput);
		
		Response response = service.login(input);
		
		assertEquals(401,response.getStatus());
	}

}
