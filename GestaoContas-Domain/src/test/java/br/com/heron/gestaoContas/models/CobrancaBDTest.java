package br.com.heron.gestaoContas.models;

import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.DESPESA_RECORRENTE;
import static br.com.heron.gestaoContas.models.CobrancaJsonHelper.MES_COBRANCA;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CobrancaBDTest extends BDTest{

	private Cobranca subject;
	
	@Before
	public void setupEnvironment(){
		DespesaRecorrente despesa = new DespesaRecorrente("teste",1);
		despesa.saveIt();
		
		MesCobranca mes = new MesCobranca(3, 2016);
		mes.saveIt();
		
		subject = despesa.geraCobranca(mes, 21.00); 
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void checkup() {
		
		List<Cobranca> cobrancas = Cobranca.findAll().include(DespesaRecorrente.class,MesCobranca.class);
		assertEquals(1,cobrancas.size());
		assertEquals("teste : Todo dia 1 - 3/2016 R$21.00",cobrancas.get(0).toString());
		assertEquals(1,cobrancas.get(0).get("despesa_id"));
	}
	
	@Test
	public void toJson_onlyDespesa(){
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_id\":1,\"valor\":21.0}", subject.toJson(DESPESA_RECORRENTE));
	}
	
	@Test
	public void toJson_onlyMes(){
		assertEquals("{\"despesa_id\":1,\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}", subject.toJson(MES_COBRANCA));
	}
	
	@Test
	public void toJson_all(){
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}", subject.toJson(DESPESA_RECORRENTE,MES_COBRANCA));
	}
	
	@Test
	public void toJson_all_method(){
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}", subject.toJsonIncludingAll());
	}
	
	@Test
	public void toJson_none(){
		assertEquals("{\"despesa_id\":1,\"id\":1,\"mes_id\":1,\"valor\":21.0}", subject.toJson());
	}
	
	@Test
	public void toMapJson_onlyCobranca(){
		assertEquals("{\"id\":1,\"valor\":21.0}", subject.toJsonMaped());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void toMapJson_withDependencies(){
		List<Cobranca> cobrancas = Cobranca.findAll().include(DespesaRecorrente.class,MesCobranca.class);
		assertEquals(1,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}", cobranca.toJsonMaped());
	}

}
