package br.com.heron.gestaoContas.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UsuarioTest extends BDTest{

	@Test
	public void autenticaUsuario() {
		new Usuario("Heron","admin","email@email.com").saveIt();
		
		assertTrue(Usuario.isAutenticado("email@email.com", "admin"));
	}

}
