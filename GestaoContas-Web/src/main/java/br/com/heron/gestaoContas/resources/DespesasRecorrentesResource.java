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

import br.com.heron.gestaoContas.controllers.DespesaRecorrenteController;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.utils.JSONConverter;

@Singleton
@Path("/despesasRecorrentes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class DespesasRecorrentesResource {

	@Inject
	private DespesaRecorrenteController controller;
	
	@Inject
	private JSONConverter converter;
	
	@GET
	public Response all() throws JsonProcessingException, IOException{
		
		List<DespesaRecorrente> all = controller.findAll();
		
		return  Response.ok(converter.convert(all).toString()).build();
	}
	
	@Path("/{id}")
	@GET
	public Response getByID(@PathParam("id") long id) throws JsonProcessingException, IOException{
		DespesaRecorrente despesa = controller.findById(id);
		return Response.ok(converter.convert(despesa)).build();
	}

	@POST
	public Response create(JsonNode object) throws URISyntaxException, JsonProcessingException, IOException {
		DespesaRecorrente despesa = controller.insert(object.get("nome").textValue(), object.get("dia_de_cobranca").asInt());
		return Response.created(new URI("/despesasRecorrentes/"+despesa.getId())).entity(converter.convert(despesa)).build();
	}
}
