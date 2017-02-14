package br.com.heron.gestaoContas.controllers.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.heron.gestaoContas.controllers.dtos.RelatorioCobrancaParametro;

public class RelatorioCobrancaParametroTest {

	private RelatorioCobrancaParametro subject = new RelatorioCobrancaParametro();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void setIds_happyDay() {
		subject.setIds("(1,2)");
		
		assertEquals(2,subject.ids_despesas.size());
		assertEquals(1l,subject.ids_despesas.get(0).longValue());
		assertEquals(2l,subject.ids_despesas.get(1).longValue());
	}
	
	@Test
	public void setIds_semIds() {
		subject.setIds("()");
		
		assertNull(subject.ids_despesas);
	}
	
	
	@Test
	public void setIds_semParametros(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		subject.setIds("1,2");
	}
	
	@Test
	public void setIds_semParametros_noInicio(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		subject.setIds("1,2)");
	}
	
	@Test
	public void setIds_semParametros_noFim(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		subject.setIds("(1,2");
	}
	
	@Test
	public void setIds_semParametros_null(){
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Filtro inválido, chame passando argumento ({filtro}*), onde os filtros podem ser {despesaRecorrente,mesCobranca}");
		subject.setIds(null);
	}
	
	

}
