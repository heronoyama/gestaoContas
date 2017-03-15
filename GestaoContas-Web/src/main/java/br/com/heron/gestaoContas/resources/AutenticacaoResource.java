package br.com.heron.gestaoContas.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import br.com.heron.gestaoContas.controllers.AutenticacaoController;

@Singleton
@Path("/oauth")
public class AutenticacaoResource {
	
	@Inject
	private AutenticacaoController controller;
	
	
	@POST
	@Path("/login")
	public Response login(JsonNode object){
		String email = object.get("email").asText();
		String senha = object.get("senha").asText();
		
		if(controller.autenticado(email, senha))
			return Response.ok().build();
		return Response.status(401).build();
		
	}
	
	@POST
	@Path("/logout")
	public Response logout(JsonNode object){
		String email = object.get("email").asText();
		String senha = object.get("senha").asText();
		
		if(controller.autenticado(email, senha))
			return Response.ok().build();
		return Response.status(401).build();
		
	}
	

}
