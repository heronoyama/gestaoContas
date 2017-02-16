package br.com.heron.gestaoContas.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.google.inject.Inject;

import br.com.heron.gestaoContas.controllers.RelatorioCobrancaController;
import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;

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
	
	private class CSVWriter implements StreamingOutput  {

		private String data;
		
		public CSVWriter(String data) {
			this.data = data;
		}

		@Override
		public void write(OutputStream output) throws IOException, WebApplicationException {
			Writer writer = new BufferedWriter(new OutputStreamWriter(output));
			writer.append(data);
			writer.flush();
		}
		
	}
		
	
	
}
