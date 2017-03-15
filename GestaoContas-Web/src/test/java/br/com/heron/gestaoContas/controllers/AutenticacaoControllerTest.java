package br.com.heron.gestaoContas.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.models.Usuario;

@RunWith(GuiceJunit4Runner.class)
public class AutenticacaoControllerTest extends BDTest{

	@Inject
	private AutenticacaoController subject;
	
	@Test
	public void autenticaSucesso() {
		new Usuario("Heron","teste","email@email.com").saveIt();
		
		Assert.assertTrue(subject.autenticado("email@email.com", "teste"));
	}

}
