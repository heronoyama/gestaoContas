package br.com.heron.gestaoContas.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.models.DespesaRecorrente;

public class JSONConverterTest extends BDTest{

	private JSONConverter subject = new JSONConverter();
	
	@Test
	public void convertObject() throws JsonProcessingException, IOException {
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		
		assertEquals("{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"}",subject.convert(despesa));
	}
	
	@Test
	public void convertList() throws JsonProcessingException, IOException {
		DespesaRecorrente despesa1 = new DespesaRecorrente("Teste1",1);
		despesa1.saveIt();
		
		DespesaRecorrente despesa2 = new DespesaRecorrente("Teste2",1);
		despesa2.saveIt();
		
		List<DespesaRecorrente> despesas = Arrays.asList(despesa1,despesa2);
		assertEquals("[{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste1\"},{\"dia_de_cobranca\":1,\"id\":2,\"nome\":\"Teste2\"}]",subject.convert(despesas));
		
	}

}
