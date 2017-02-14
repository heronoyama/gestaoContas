package br.com.heron.gestaoContas.models;

import org.javalite.activejdbc.Model;

public class GestaoContasModel extends Model{

	public String toJson(){
		return super.toJson(false);
	}
	
}
