package br.com.heron.gestaoContas.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Inject;

import br.com.heron.gestaoContas.controllers.RelatorioCobrancaController;
import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;
import br.com.heron.gestaoContas.resources.utils.CSVWriter;

@Path("/relatorios/csv/cobranca")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(APPLICATION_JSON)
public class RelatorioCobrancaCSVResource extends RelatorioCobrancaResource{

	@Inject
	private RelatorioCobrancaController controller;
	
	@Override
	protected Response buildResponse(RelatorioCobrancaParametro parametro)	throws JsonGenerationException, JsonMappingException, IOException {
		
		CSVWriter writer = new CSVWriter(controller.getRelatorioCSV(parametro));
		
		ResponseBuilder response = Response.ok(writer);
		response.header("Content-Disposition", "attachment; filename=report.csv");
		return response.build();
	}
	
	
	
}
