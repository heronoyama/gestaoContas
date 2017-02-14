package br.com.heron.gestaoContas.relatorios;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.heron.gestaoContas.models.BDTest;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class RelatorioCobrancaCondicaoTest extends BDTest{

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void validate_mesesErrados() {
		RelatorioCobrancaCondicaoBuilder builder = new RelatorioCobrancaCondicaoBuilder();
		builder.setMesInicial(new MesCobranca(3,2016)).setMesFinal(new MesCobranca(2,2016));
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Mes final deve ser antes ou igual ao Mes Inicial");
		builder.build();
	}
	
	@Test
	public void getCobranca_onlyOneDespesa_withMes(){
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		MesCobranca mes = new MesCobranca(3,2016);
		mes.saveIt();
		despesa.geraCobranca(mes, 21.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).setMes(mes).build();
		List<Cobranca> cobrancas = condicao.getCobrancas();
		assertEquals(1,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",cobranca.toJsonMaped());
	}
	
	@Test
	public void getCobranca_onlyOneDespesa_noMes(){
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		MesCobranca mes = new MesCobranca(3,2016);
		mes.saveIt();
		despesa.geraCobranca(mes, 21.00);
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		List<Cobranca> cobrancas = condicao.getCobrancas();
		assertEquals(1,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",cobranca.toJsonMaped());
		
	}
	
	@Test
	public void getCobrancas_twoDespsesas_byDespesa(){
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		despesa.geraCobranca(mes1, 21.00);
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		despesa.geraCobranca(mes2, 9.00);
		
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa).build();
		List<Cobranca> cobrancas = condicao.getCobrancas();
		assertEquals(2,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",cobranca.toJsonMaped());
		
		cobranca = cobrancas.get(1);
		assertEquals("Teste : Todo dia 1 - 4/2016 R$9.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":9.0}",cobranca.toJsonMaped());
	}
	
	@Test
	public void getCobrancas_twoDespsesas_byMesInicioEFim(){
		DespesaRecorrente despesa = new DespesaRecorrente("Teste",1);
		despesa.saveIt();
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		despesa.geraCobranca(mes1, 21.00);
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		despesa.geraCobranca(mes2, 9.00);
		
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().setMesInicial(mes1).setMesFinal(mes2).build();
		List<Cobranca> cobrancas = condicao.getCobrancas();
		assertEquals(2,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",cobranca.toJsonMaped());
		
		cobranca = cobrancas.get(1);
		assertEquals("Teste : Todo dia 1 - 4/2016 R$9.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":9.0}",cobranca.toJsonMaped());
	}
	
	@Test
	public void getCobrancas_duasDespesasCadastradas_buscaUmaDespesa(){
		DespesaRecorrente despesa1 = new DespesaRecorrente("Teste",1);
		despesa1.saveIt();
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		despesa1.geraCobranca(mes1, 21.00);
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		despesa1.geraCobranca(mes2, 9.00);
		
		DespesaRecorrente despesaExtra = new DespesaRecorrente("Extra",1);
		despesaExtra.saveIt();
		despesaExtra.geraCobranca(mes1, 10.00);
		despesaExtra.geraCobranca(mes2, 100.00);
		
		
		RelatorioCobrancaCondicao condicao = new RelatorioCobrancaCondicaoBuilder().addDespesa(despesa1).setMesInicial(mes1).setMesFinal(mes2).build();
		List<Cobranca> cobrancas = condicao.getCobrancas();
		assertEquals(2,cobrancas.size());
		Cobranca cobranca = cobrancas.get(0);
		assertEquals("Teste : Todo dia 1 - 3/2016 R$21.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":1,\"mes_cobranca\":{\"ano\":2016,\"id\":1,\"mes\":3},\"valor\":21.0}",cobranca.toJsonMaped());
		
		cobranca = cobrancas.get(1);
		assertEquals("Teste : Todo dia 1 - 4/2016 R$9.00",cobranca.toString());
		
		assertEquals("{\"despesa_recorrente\":{\"dia_de_cobranca\":1,\"id\":1,\"nome\":\"Teste\"},\"id\":2,\"mes_cobranca\":{\"ano\":2016,\"id\":2,\"mes\":4},\"valor\":9.0}",cobranca.toJsonMaped());
	}

}
