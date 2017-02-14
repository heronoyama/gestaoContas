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
import com.google.inject.Singleton;

import br.com.heron.gestaoContas.controllers.CobrancaController;
import br.com.heron.gestaoContas.controllers.dtos.CriacaoCobrancaParametro;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.utils.CobrancaHelperResolver;
import br.com.heron.gestaoContas.utils.JSONConverter;

@Singleton
@Path("/cobrancas")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class CobrancaResource {
	
	@Inject
	private CobrancaController controller;
	
	@Inject
	private JSONConverter converter;
	
	@GET
	@Path("/{id_despesa}")
	public Response getCobrancasByDespesa(@PathParam("id_despesa") long id_despesa) throws JsonProcessingException, IOException{
		List<Cobranca> cobrancas = controller.getCobrancas(id_despesa);
		return Response.ok(converter.convert(cobrancas)).build();
	}
	
	@GET
	@Path("{id_despesa}/filters{filtros}")
	public Response getCobrancasByDespesaHelper(@PathParam("id_despesa") long id_despesa, @PathParam("filtros") String filtros) throws JsonProcessingException, IOException{
		List<Cobranca> cobrancas = controller.getCobrancas(id_despesa);
		CobrancaHelperResolver resolver = new CobrancaHelperResolver(filtros);
		return Response.ok(converter.convert(cobrancas,resolver)).build();
	}
	
	@GET
	@Path("/{mes}/{ano}")
	public Response getCobrancaByMes(@PathParam("mes")int mes, @PathParam("ano") int ano) throws JsonProcessingException, IOException{
		List<Cobranca> cobrancas = controller.getCobrancas(mes, ano);
		return Response.ok(converter.convert(cobrancas)).build();
	}
	
	@GET
	@Path("/{mes}/{ano}/filters{filtros}")
	public Response getCobrancaByMesHelper(@PathParam("mes")int mes, @PathParam("ano") int ano,@PathParam("filtros")String filtros) throws JsonProcessingException, IOException{
		List<Cobranca> cobrancas = controller.getCobrancas(mes, ano);
		CobrancaHelperResolver resolver = new CobrancaHelperResolver(filtros);
		return Response.ok(converter.convert(cobrancas,resolver)).build();
	}
	
	@POST
	public Response geraCobranca(JsonNode object) throws URISyntaxException, JsonProcessingException, IOException{
		CriacaoCobrancaParametro parametro = new CriacaoCobrancaParametro(object);

		Cobranca cobranca = controller.geraCobranca(parametro);
		
		CobrancaHelperResolver resolver = CobrancaHelperResolver.allResolvers();
		return Response.created(new URI("/cobrancas/"+parametro.id_despesa)).entity(converter.convert(cobranca,resolver)).build();
	}

}