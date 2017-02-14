package br.com.heron.gestaoContas.controllers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import br.com.heron.gestaoContas.BDTest;
import br.com.heron.gestaoContas.GuiceJunit4Runner;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;

@RunWith(GuiceJunit4Runner.class)
public class CobrancaControllerTest extends BDTest{

	@Inject
	private CobrancaController subject;
	
	@Inject
	private DespesaRecorrenteController despesaController;
	
	@Inject
	private MesCobrancaController mesController;
	
	@Test
	public void geraDespesa_happyDay() {
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		mesController.insert(3, 2016);
		
		Cobranca cobranca = subject.geraCobranca(Long.parseLong(despesa.getId().toString()), 3, 2016, 21.00);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
	}
	
	//TODO failtests

	@Test
	public void getCobrancas_despesa_duasCobrancas(){
		mesController.insert(3, 2016);
		mesController.insert(4, 2016);
		
		DespesaRecorrente despesa = despesaController.insert("Teste", 1);
		
		Long despesa_id = Long.parseLong(despesa.getId().toString());
		
		subject.geraCobranca(despesa_id, 3, 2016, 21.00);
		subject.geraCobranca(despesa_id, 4, 2016, 5.00);
		
		List<Cobranca> cobrancas = subject.getCobrancas(despesa_id);
		assertEquals(2,cobrancas.size());
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobrancas.get(0).toString());
		assertEquals("Teste : Todo dia 1 - 4/2016 R$5.00",cobrancas.get(1).toString());
		
	}
	
	@Test
	public void getCobrancas_meses_duasCobrancas(){
		mesController.insert(3, 2016);
				
		DespesaRecorrente despesa1 = despesaController.insert("Teste", 1);
		DespesaRecorrente despesa2 = despesaController.insert("Teste2", 1);
		
		Long despesa1_id = Long.parseLong(despesa1.getId().toString());
		Long despesa2_id = Long.parseLong(despesa2.getId().toString());
		
		subject.geraCobranca(despesa1_id, 3, 2016, 21.00);
		subject.geraCobranca(despesa2_id, 3, 2016, 5.00);
		
		List<Cobranca> cobrancas = subject.getCobrancas(3,2016);
		assertEquals(2,cobrancas.size());
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobrancas.get(0).toString());
		assertEquals("Teste2 : Todo dia 1 - 3/2016 R$5.00",cobrancas.get(1).toString());
		
	}
	
}
