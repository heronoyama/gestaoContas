package br.com.heron.gestaoContas.controllers;

import com.google.inject.Singleton;

import br.com.heron.gestaoContas.models.Usuario;

@Singleton
public class AutenticacaoController {
	
	public boolean autenticado(String email, String senha){
		return Usuario.isAutenticado(email, senha);
	}

}
