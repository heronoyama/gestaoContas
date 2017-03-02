package br.com.heron.gestaoContas.resources.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class CSVWriter implements StreamingOutput  {

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