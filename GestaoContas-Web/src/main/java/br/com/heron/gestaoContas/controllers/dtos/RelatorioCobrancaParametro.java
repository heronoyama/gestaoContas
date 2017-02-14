package br.com.heron.gestaoContas.controllers.dtos;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import br.com.heron.gestaoContas.models.CobrancaJsonHelper;

public class RelatorioCobrancaParametro {

	public List<Long> ids_despesas;
	public Long id_mes_inicio;
	public Long id_mes_fim;

	//TODO tests
	public void setIds(String ids){
		if(isNullOrEmpty(ids) || !ids.startsWith("(") || !ids.endsWith(")"))
			throw throwsInvalidArgument();
		
		String cleanedFilter = ids.substring(1, ids.length()-1).trim();
		if(cleanedFilter.length() == 0)
			return;
		
		String[] splited = cleanedFilter.split(",");
		
		this.ids_despesas = new ArrayList<>();
		for (String id : splited) {
			this.ids_despesas.add(Long.parseLong(id));
		}
			
	}

	private IllegalArgumentException throwsInvalidArgument() {
		return new IllegalArgumentException(
				"Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser "
						+ CobrancaJsonHelper.listaPossibilidades());
	}

}
