package br.com.heron.gestaoContas.utils;

import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.DESPESA_RECORRENTE;
import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.MES_COBRANCA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CobrancaHelperResolverTest {

	private CobrancaHelperResolver subject;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void noFilter() {
		subject = new CobrancaHelperResolver("()");
		assertTrue(subject.helpers().isEmpty());
	}
	
	@Test
	public void fail_noFilter(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		new CobrancaHelperResolver("");
		
	}
	
	@Test
	public void fail_notFormatted(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		new CobrancaHelperResolver("despesaRecorrente");
	}
	
	@Test
	public void fail_noFilterFound(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		new CobrancaHelperResolver("(invalidaFilter)");
	}
	
	@Test
	public void pass_FiltroDespesa(){
		subject = new CobrancaHelperResolver("(despesaRecorrente)");
		assertEquals(1,subject.helpers().size());
		assertEquals(DESPESA_RECORRENTE,subject.helpers().get(0));
	}
	
	@Test
	public void pass_FiltroMes(){
		subject = new CobrancaHelperResolver("(mesCobranca)");
		assertEquals(1,subject.helpers().size());
		assertEquals(MES_COBRANCA,subject.helpers().get(0));
	}
	
	@Test
	public void pass_twoFilters(){
		subject = new CobrancaHelperResolver("(despesaRecorrente,mesCobranca)");
		assertEquals(2,subject.helpers().size());
		assertEquals(DESPESA_RECORRENTE,subject.helpers().get(0));
		assertEquals(MES_COBRANCA,subject.helpers().get(1));
	}

}
