package br.com.heron.gestaoContas.finders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.base.Joiner;

import br.com.heron.gestaoContas.models.MesCobranca;

public class MesCobrancaFinderSqlBuilder {
	private MesCobranca from;
	private MesCobranca to;

	public MesCobrancaFinderSqlBuilder(MesCobranca from, MesCobranca to) {
		this.from = from;
		this.to = to;
	}
	
	public String getSQL(){
		if(from.after(to))
			throw new IllegalArgumentException("Mes fim deve ser depois ou igual ao Mes inicio");
		
		if(from.ano().equals(to.ano()))
			return mesmoAno();
		
		List<String> sqls = new ArrayList<>();
		sqls.add(getMesesFaltantesFrom());
		if(to.ano() - from.ano() > 1)
			sqls.add(getAnosFaltantes());
		sqls.add(getMesesFaltantesTo());

		return Joiner.on(" or ").join(sqls);
		
	}

	private String getAnosFaltantes() {
		return String.format("(ano in (%s))",Joiner.on(",").join(IntStream.range(from.ano()+1, to.ano()).iterator()));
	}

	private String getMesesFaltantesTo() {
		return getMesesFaltantes(1,to.mes()+1,to.ano());
	}

	private String getMesesFaltantesFrom() {
		return getMesesFaltantes(from.mes(),13,from.ano());
	}

	private String mesmoAno() {
		return getMesesFaltantes(from.mes(),to.mes()+1,from.ano());
	}
	
	private String getMesesFaltantes(int from, int to,int ano){
		return String.format("(mes in (%s) and ano = %d)",Joiner.on(",").join(IntStream.range(from,to).iterator()),ano);
	}
	
}
