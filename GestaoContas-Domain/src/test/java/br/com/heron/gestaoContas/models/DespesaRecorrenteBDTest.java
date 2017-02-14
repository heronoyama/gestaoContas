package br.com.heron.gestaoContas.models;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DespesaRecorrenteBDTest extends BDTest {

	@Test
	public void testCRUD() {
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		
		DespesaRecorrente busca = DespesaRecorrente.findFirst("nome = ?", "Teste");
		Assert.assertEquals("Teste", busca.get("nome"));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void geraCobranca(){
		MesCobranca mesCobranca = new MesCobranca(3,2016);
		mesCobranca.saveIt();
		
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		
		List<Cobranca> cobrancas = Cobranca.findAll().include(DespesaRecorrente.class,MesCobranca.class);
		assertEquals(0,cobrancas.size());
		
		Cobranca cobranca = despesa.geraCobranca(mesCobranca,21.00);
		
		cobrancas = Cobranca.findAll().include(DespesaRecorrente.class,MesCobranca.class);
		assertEquals(1,cobrancas.size());
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobrancas.get(0).toString());
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
	}

}
