package br.com.heron.gestaoContas.controllers;

import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import com.google.inject.Singleton;

import br.com.heron.gestaoContas.models.MesCobranca;

@Singleton
public class MesCobrancaController {

	public List<MesCobranca> findAll(){
		return MesCobranca.findAll();
	}

	public MesCobranca insert(int mes, int ano) {
		MesCobranca mesCobranca = new MesCobranca(mes, ano);
		mesCobranca.saveIt();
		return mesCobranca;
	}

	public MesCobranca find(int mes, int ano) {
		
		List<MesCobranca> result = MesCobranca.find("mes = ? && ano = ?", mes,ano);
		//TODO após consistencias do MesCobranca, esse erro some. Além do mais, deve ser criado um finder especifico
		/*if(result.size() > 1){
			throw new InternalServerErrorException("Deu caca, mes repetido");
		}*/
		
		return result.size() == 1 ? result.get(0) : null;
	}

	public MesCobranca findOrCreate(int mes, int ano) {
		MesCobranca result = find(mes,ano);
		if(result == null)
			result = insert(mes,ano);
		return result;
	}

	public MesCobranca findById(long id) {
		return MesCobranca.findById(id);
	}

	
}