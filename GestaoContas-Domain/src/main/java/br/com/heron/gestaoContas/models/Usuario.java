package br.com.heron.gestaoContas.models;

import java.util.List;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("user")
public class Usuario extends GestaoContasModel {

	public Usuario(){}
	
	public Usuario(String nome, String senha, String email){
		set("nome",nome);
		set("senha",senha);
		set("email",email);
	}
	
	public static boolean isAutenticado(String email, String senha){
		
		List<Model> usuarios = Usuario.find("email = ? and senha = ?", email,senha);
		assert usuarios.size() <= 1;
		
		return usuarios.size() == 1;
	}
	
}