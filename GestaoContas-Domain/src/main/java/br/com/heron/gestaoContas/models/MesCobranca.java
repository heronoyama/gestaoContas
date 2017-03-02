package br.com.heron.gestaoContas.models;

import java.util.Collections;
import java.util.List;

import org.javalite.activejdbc.annotations.Table;

import com.google.api.client.repackaged.com.google.common.base.Objects;

import br.com.heron.gestaoContas.finders.MesCobrancaFinderSqlBuilder;

@Table("meses_cobranca")
public class MesCobranca extends GestaoContasModel implements Comparable<MesCobranca>{

	public MesCobranca (){}
	
	public MesCobranca(int mes, int ano){
		set("mes",mes);
		set("ano",ano);
	}
	
	public static List<MesCobranca> meses(MesCobranca from, MesCobranca to){
		MesCobrancaFinderSqlBuilder builder = new MesCobrancaFinderSqlBuilder(from, to);
		
		List<MesCobranca> meses = MesCobranca.find(builder.getSQL());
		Collections.sort(meses);
		return meses;
	}
	
	public static List<MesCobranca> mesesAte(MesCobranca mesAte) {
		MesCobranca primeiroMes = primeiroMes();
		
		return meses(primeiroMes,mesAte);
	}

	static MesCobranca primeiroMes() {
		List<MesCobranca> meses = MesCobranca.findBySQL("select * from meses_cobranca order by convert(concat(ano,'-',mes,'-1'),DATE) limit 1");
		assert meses.size() == 1;
		return meses.get(0);
	}
	
	static MesCobranca ultimoMes(){
		List<MesCobranca> meses = MesCobranca.findBySQL("select * from meses_cobranca order by convert(concat(ano,'-',mes,'-1'),DATE) DESC limit 1");
		assert meses.size() == 1;
		return meses.get(0);
	}
	
	public static List<MesCobranca> mesesAPartirDe(MesCobranca mesInicial) {
		MesCobranca ultimoMes = ultimoMes();
		return meses(mesInicial,ultimoMes);
	}

	@Override
	public String toString() {
		return String.format("%d/%d", getInteger("mes"),getInteger("ano"));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(!(obj instanceof MesCobranca))
			return false;
		
		MesCobranca cObj = (MesCobranca) obj;
		return mes().equals(cObj.mes()) && ano().equals(cObj.ano());
					
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(mes(),ano());
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

	@Override
	public int compareTo(MesCobranca o) {
		if(ano().equals(o.ano()))
			return mes().compareTo(o.mes());
		return (ano() > o.ano())? 1 : -1;
			
	}

	
	
}
