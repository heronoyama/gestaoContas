package br.com.heron.gestaoContas.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.models.MesCobranca;

public class MesCobrancaControllerTest extends BDTest{

	private MesCobrancaController subject = new MesCobrancaController();
	
	@Test
	public void checkupCreation() {
		assertEquals(0,subject.findAll().size());
		
		subject.insert(3, 2016);
		
		assertEquals(1,subject.findAll().size());
		assertEquals("3/2016", subject.findAll().get(0).toString());
	}
	
	@Test
	public void buscaPorMesAno(){
		subject.insert(3, 2016);
		
		MesCobranca result = subject.find(3,2016);
		assertEquals("3/2016", result.toString());
	}
	
	@Test
	public void buscaOuCria_NaoExiste(){
		assertEquals(0,subject.findAll().size());
		
		subject.findOrCreate(3,2016);
		
		MesCobranca result = subject.find(3, 2016);
		assertEquals("3/2016", result.toString());
	}
	
	@Test
	public void buscaOuCria_Existe(){
		subject.insert(3, 2016);
		assertEquals(1,subject.findAll().size());
		
		MesCobranca result = subject.findOrCreate(3,2016);
		
		assertEquals(1,subject.findAll().size());
		assertEquals("3/2016", result.toString());
	}

}