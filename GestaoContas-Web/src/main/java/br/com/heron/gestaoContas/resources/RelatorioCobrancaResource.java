package br.com.heron.gestaoContas.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;

import br.com.heron.gestaoContas.controllers.MesCobrancaController;
import br.com.heron.gestaoContas.controllers.RelatorioCobrancaController;
import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;
import br.com.heron.gestaoContas.models.MesCobranca;


@Singleton
@Path("/relatorios/cobranca")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class RelatorioCobrancaResource {

	@Inject
	private RelatorioCobrancaController controller;
	
	@Inject
	private MesCobrancaController mesController;
	
	@GET
	@Path("/despesas{ids_despesa}/from({mes_inicio})/to({mes_fim})")
	public Response cobrancasDasDespesasFromTo(@PathParam("ids_despesa") String ids, @PathParam("mes_inicio") long idMesInicio, @PathParam("mes_fim") long idMesFim) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.id_mes_inicio = idMesInicio;
		parametro.id_mes_fim = idMesFim;
		parametro.setIds(ids);
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
		
	}
	
	@GET
	@Path("/despesas{ids_despesa}")
	public Response cobrancasDasDespesas(@PathParam("ids_despesa") String ids) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.setIds(ids);
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
		
	}
	
	@GET
	@Path("/from({mes_inicio})/to({mes_fim})")
	public Response cobrancasFromTo(@PathParam("mes_inicio") long idMesInicio, @PathParam("mes_fim") long idMesFim) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.id_mes_inicio = idMesInicio;
		parametro.id_mes_fim = idMesFim;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/despesas{ids_despesa}/of({id_mes})")
	public Response cobrancasDasDespesasOfMes(@PathParam("ids_despesa") String ids, @PathParam("id_mes") long idMes) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.setIds(ids);
		parametro.id_mes_inicio = idMes;
		parametro.id_mes_fim = idMes;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/of({id_mes})")
	public Response cobrancasOfMes(@PathParam("id_mes") long idMes) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.id_mes_inicio = idMes;
		parametro.id_mes_fim = idMes;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/despesas{ids_despesas}/from({id_mes})")
	public Response cobrancasDespesasFrom(@PathParam("ids_despesa") String ids, @PathParam("id_mes")long idMesFrom) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.setIds(ids);
		parametro.id_mes_inicio = idMesFrom;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/despesas{ids_despesas}/to({id_mes})")
	public Response cobrancasDasDespesasTo(@PathParam("ids_despesa") String ids, @PathParam("id_mes")long idMesTo) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.setIds(ids);
		parametro.id_mes_fim = idMesTo;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/from({id_mes})")
	public Response cobrancasFrom(@PathParam("id_mes")long idMesFrom) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.id_mes_inicio = idMesFrom;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/to({id_mes})")
	public Response cobrancasTo(@PathParam("id_mes")long idMesTo) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		parametro.id_mes_fim = idMesTo;
		
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
	@GET
	@Path("/mesCobranca({mes},{ano})")
	public Response cobrancasDoMesAno(@PathParam("mes") int mes, @PathParam("ano") int ano) throws JsonGenerationException, JsonMappingException, IOException{
		RelatorioCobrancaParametro parametro = new RelatorioCobrancaParametro();
		MesCobranca mesCobranca = mesController.find(mes, ano);
		if(mesCobranca == null)
			return Response.ok("[]").build();
		
		Long mesId = mesCobranca.getLongId();
		parametro.id_mes_inicio = mesId;
		parametro.id_mes_fim = mesId;
		return Response.ok(controller.getRelatorioJson(parametro)).build();
	}
	
}
