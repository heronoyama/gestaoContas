package br.com.heron.gestaoContas.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.heron.gestaoContas.BDTest;

public class DespesaRecorrenteControllerTest extends BDTest{

	private DespesaRecorrenteController subject = new DespesaRecorrenteController();
	
	@Test
	public void checkupCreation() {
		assertEquals(0,subject.findAll().size());
		
		subject.insert("Teste", 1);
		
		assertEquals(1,subject.findAll().size());
		assertEquals("Teste",subject.findById(1).get("nome"));
		
		
	}

}
