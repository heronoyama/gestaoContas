package br.com.heron.gestaoContas.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;

import br.com.heron.gestaoContas.controllers.MesCobrancaController;
import br.com.heron.gestaoContas.models.MesCobranca;
import br.com.heron.gestaoContas.utils.JSONConverter;

@Singleton
@Path("/mesesCobranca")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class MesCobrancaResource {

	@Inject
	private MesCobrancaController controller;
	
	@Inject
	private JSONConverter converter;
	
	@GET
	public Response all() throws JsonProcessingException, IOException{
		
		List<MesCobranca> all = controller.findAll();
		
		return  Response.ok(converter.convert(all)).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") long id) throws JsonProcessingException, IOException{
		MesCobranca mes = controller.findById(id);
		return Response.ok(converter.convert(mes)).build();
	}
	
	@GET
	@Path("/{mes}/{ano}")
	//TODO ToTest
	public Response findMes(@PathParam("mes") int mes, @PathParam("ano") int ano) throws JsonProcessingException, IOException{
		MesCobranca mesCobranca = controller.find(mes, ano);
		if(mesCobranca != null)
			return Response.ok(converter.convert(mesCobranca)).build();
		return Response.ok().build();
	}

	@POST
	public Response create(JsonNode input) throws URISyntaxException, JsonProcessingException, IOException {
		int mes = input.get("mes").intValue();
		int ano = input.get("ano").intValue();
		
		MesCobranca mesCobranca = controller.insert(mes,ano);
		return Response.created(new URI("/mesesCobranca/"+mesCobranca.getId())).entity(converter.convert(mesCobranca)).build();
	}
	
}
