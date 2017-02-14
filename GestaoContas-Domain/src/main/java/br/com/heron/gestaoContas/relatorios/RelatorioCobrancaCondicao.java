package br.com.heron.gestaoContas.relatorios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class RelatorioCobrancaCondicao {
	
	private MesCobranca mesInicial;
	private MesCobranca mesFinal;
	private List<DespesaRecorrente> despesas;
	private List<String> conditions = new ArrayList<>();

	RelatorioCobrancaCondicao(MesCobranca mesInicial, MesCobranca mesFinal, Set<DespesaRecorrente> despesas) {
		this.mesInicial = mesInicial;
		this.mesFinal = mesFinal;
		this.despesas = Arrays.asList(despesas.toArray(new DespesaRecorrente[]{}));
		
		build();
	}

	private void build() {
		
		if(!despesas.isEmpty()){
			List<String> ids = new DespesaRecorrenteIdGetter().collect(despesas);
			conditions.add(String.format("despesa_id in (%s)",Joiner.on(",").join(ids)));
		}
	
		if(mesInicial != null)
			conditions.add(conditionMes());
	}

	@SuppressWarnings("unchecked")
	public List<Cobranca> getCobrancas() {
		List<Cobranca> cobrancas = Cobranca.<Cobranca>find(getQuery()).include(DespesaRecorrente.class,MesCobranca.class);
		return cobrancas;
	}

	private String getQuery() {
		return Joiner.on(" && ").join(conditions);
	}
	
	private String conditionMes(){
		if(mesFinal.equals(mesInicial))
			return "mes_id = "+mesInicial.getLongId().toString();
		
		List<MesCobranca> meses = MesCobranca.meses(mesInicial, mesFinal);
		List<String> ids = new MesCobrancaIdCollector().collect(meses);
		return String.format("mes_id in (%s)", Joiner.on(",").join(ids));
	}

	private static class DespesaRecorrenteIdGetter implements Function<DespesaRecorrente, String> {
		@Override
		public String apply(DespesaRecorrente t) {
			return t.getLongId().toString();
		}
		
		public List<String> collect(List<DespesaRecorrente> despesas){
			return despesas.stream().map(this).collect(Collectors.toCollection(ArrayList::new));
		}
	}
	
	private static class MesCobrancaIdCollector implements Function<MesCobranca, String>{
		@Override
		public String apply(MesCobranca t) {
			return t.getLongId().toString();
		}
		
		public List<String> collect(List<MesCobranca> meses){
			return meses.stream().map(this).collect(Collectors.toCollection(ArrayList::new));
		}
	}
	
}
