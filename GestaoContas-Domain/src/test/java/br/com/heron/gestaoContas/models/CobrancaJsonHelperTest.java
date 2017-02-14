package br.com.heron.gestaoContas.models;

import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.DESPESA_RECORRENTE;
import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.MES_COBRANCA;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CobrancaJsonHelperTest extends BDTest{

	private Cobranca subject;
	
	@Before
	public void setupEnvironment(){
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		
		MesCobranca mesCobranca = new MesCobranca(3,2016);
		mesCobranca.saveIt();
		
		subject = despesa.geraCobranca(mesCobranca, 21.00);
	}
	
	@Test
	public void replace_despesa_singleConvertion() {
		assertEquals("\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"}", DESPESA_RECORRENTE.replace("\"despesa_id\":1",subject));
	}
	
	@Test
	public void replace_mesCobranca_singleConvertion() {
		assertEquals("\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3}", MES_COBRANCA.replace("\"mes_id\":1",subject));
	}
	
	@Test
	public void replace_complexConvertion(){
		String expected = "{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}";
		String json = DESPESA_RECORRENTE.replace(subject.toJson(false),subject);
		json = MES_COBRANCA.replace(json, subject);
		assertEquals(expected,json);
	}

	@Test
	public void allValores(){
		String expected = "(despesaRecorrente,mesCobranca)";
		assertEquals(expected,CobrancaJsonHelper.allHelpers());
	}
	
	

}
