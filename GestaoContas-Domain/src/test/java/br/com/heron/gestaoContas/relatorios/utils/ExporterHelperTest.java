package br.com.heron.gestaoContas.relatorios.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.com.heron.gestaoContas.models.BDTest;
import br.com.heron.gestaoContas.models.Cobranca;
import br.com.heron.gestaoContas.models.DespesaRecorrente;
import br.com.heron.gestaoContas.models.MesCobranca;

public class ExporterHelperTest extends BDTest{

	
	private DespesaRecorrente despesa;
	private DespesaRecorrente despesa2;
	private MesCobranca mes;
	private MesCobranca mes2;
	
	@Before
	public void setupEnvironment(){
		despesa = new DespesaRecorrente("teste",1);
		despesa.saveIt();
		mes = new MesCobranca(3,2016);
		mes.saveIt();
		
		despesa2 = new DespesaRecorrente("teste2",10);
		despesa2.saveIt();
		
		mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
	}
	
	@Test
	public void getDespesas_umaCobranca() {
		Cobranca cobranca = despesa.geraCobranca(mes, 15.00);
		ExporterHelper helper = new ExporterHelper(Arrays.asList(cobranca));
		Set<DespesaRecorrente> despesas = helper.getDespesas();
		assertEquals(1,despesas.size());
	}
	
	@Test
	public void getMeses_umaCobranca(){
		Cobranca cobranca = despesa.geraCobranca(mes, 15.00);
		ExporterHelper helper = new ExporterHelper(Arrays.asList(cobranca));
		Set<MesCobranca> meses = helper.getMeses();
		assertEquals(1,meses.size());
	}
	
	@Test
	public void getValor_umaCobranca(){
		Cobranca cobranca = despesa.geraCobranca(mes, 15.00);
		ExporterHelper helper = new ExporterHelper(Arrays.asList(cobranca));
		
		assertEquals(1,15.00,helper.getValor(despesa,mes));
		
	}
	
	@Test
	public void getDespesas_duasCobranca() {

		
		
		ExporterHelper helper = new ExporterHelper(geraVariasCobrancas());
		Set<DespesaRecorrente> despesas = helper.getDespesas();
		assertEquals(2,despesas.size());
	}
	
	@Test
	public void getMeses_duasCobranca(){
		ExporterHelper helper = new ExporterHelper(geraVariasCobrancas());
		Set<MesCobranca> meses = helper.getMeses();
		assertEquals(2,meses.size());
	}
	
	@Test
	public void getValor_duasCobranca(){
		ExporterHelper helper = new ExporterHelper(geraVariasCobrancas());
		
		assertEquals(1,5.00,helper.getValor(despesa,mes));
		assertEquals(1,10.00,helper.getValor(despesa2,mes));
		assertEquals(1,15.00,helper.getValor(despesa,mes2));
		assertEquals(1,10.00,helper.getValor(despesa2,mes2));
		
	}

	private List<Cobranca> geraVariasCobrancas(){
		
		Cobranca c = despesa.geraCobranca(mes, 5.00);
		Cobranca c2 = despesa2.geraCobranca(mes, 10.00);
		Cobranca c3 = despesa.geraCobranca(mes2, 15.00);
		Cobranca c4 = despesa2.geraCobranca(mes2, 20.00);
		
		return Arrays.asList(c,c2,c3,c4);
		
	}

}
