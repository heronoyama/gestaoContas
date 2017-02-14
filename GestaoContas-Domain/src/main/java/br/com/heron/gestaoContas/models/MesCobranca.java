package br.com.heron.gestaoContas.models;

import java.util.List;

import org.javalite.activejdbc.annotations.Table;

import br.com.heron.gestaoContas.finders.MesCobrancaFinderSqlBuilder;

@Table("meses_cobranca")
public class MesCobranca extends GestaoContasModel{

	public MesCobranca (){}
	
	public MesCobranca(int mes, int ano){
		set("mes",mes);
		set("ano",ano);
	}
	
	public static List<MesCobranca> meses(MesCobranca from, MesCobranca to){
		MesCobrancaFinderSqlBuilder builder = new MesCobrancaFinderSqlBuilder(from, to);
		
		return MesCobranca.find(builder.getSQL());
	}
	
	@Override
	public String toString() {
		return String.format("%d/%d", getInteger("mes"),getInteger("ano"));
	}

	//Equivalente à >
	public boolean after(MesCobranca other) {
		if(ano() < other.ano())
			return false;
		if(ano() > other.ano())
			return true;
		
		return mes() > other.mes();
	}

	public Integer mes() {
		return this.getInteger("mes");
	}

	public Integer ano() {
		return this.getInteger("ano");
	}
	
	public boolean equals(MesCobranca other) {
		return this.mes().equals(other.mes()) && this.ano().equals(other.ano());
	}
	
}
