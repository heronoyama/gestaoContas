package br.com.heron.gestaoContas.utils;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import br.com.heron.gestaoContas.models.CobrancaJsonHelper;

public class CobrancaHelperResolver {
	
	private String filtros;
	private List<CobrancaJsonHelper> helpers = new ArrayList<>();
	
	public CobrancaHelperResolver(String filtros) {
		this.filtros = filtros;
		validateAndSplit();
	}

	private void validateAndSplit() {
		if(isNullOrEmpty(filtros) || (!filtros.startsWith("(") && !filtros.endsWith("(")))
			throw throwsInvalidArgument();
		
		String cleanedFilter = filtros.substring(1, filtros.length()-1).trim();
		if(cleanedFilter.length() == 0)
			return;
		
		String[] splited = cleanedFilter.split(",");
		
		for (String filter : splited) {
			CobrancaJsonHelper foundFilter = CobrancaJsonHelper.get(filter);
			if(foundFilter == null)
				throw throwsInvalidArgument();
			helpers.add(foundFilter);
		}
	}

	private IllegalArgumentException throwsInvalidArgument() {
		return new IllegalArgumentException("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser "+CobrancaJsonHelper.listaPossibilidades());
	}

	public List<CobrancaJsonHelper> helpers() {
		return helpers;
	}
	
	public CobrancaJsonHelper[] helpersAsArray() {
		return helpers.toArray(new CobrancaJsonHelper[]{});
	}

	public static CobrancaHelperResolver allResolvers() {
		return new CobrancaHelperResolver(CobrancaJsonHelper.allHelpers());
	}

	

}
