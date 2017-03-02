package br.com.heron.gestaoContas.relatorios.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class ExporterHelper {
	
	private Map<DespesaRecorrente,Map<MesCobranca,Double>> map = new TreeMap<>();
	private Set<MesCobranca> meses = new TreeSet<>();

	public ExporterHelper(List<Cobranca> cobrancas) {
		for (Cobranca cobranca : cobrancas) {
			DespesaRecorrente despesa = cobranca.despesa();
			if(!map.containsKey(despesa))
				map.put(despesa, new TreeMap<>());
			MesCobranca mesCobranca = cobranca.mesCobranca();
			map.get(despesa).put(mesCobranca, cobranca.valor());
			meses.add(mesCobranca);
		}
		
	}

	public Set<DespesaRecorrente> getDespesas() {
		return map.keySet();
	}
	
	public Set<MesCobranca> getMeses(){
		return meses;
	}

	public Double getValor(DespesaRecorrente despesa, MesCobranca mes) {
		return map.get(despesa).get(mes);
	}
	
	

}
