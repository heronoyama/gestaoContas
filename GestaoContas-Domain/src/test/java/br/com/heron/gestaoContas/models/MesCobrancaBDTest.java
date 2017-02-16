package br.com.heron.gestaoContas.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class MesCobrancaBDTest extends BDTest{

	@Test
	public void checkup() {
		MesCobranca mes = new MesCobranca(3,2016);
		mes.saveIt();
		
		MesCobranca recuperado = MesCobranca.findFirst("mes = ? && ano = ?", 3,2016);
		assertEquals("3/2016",recuperado.toString());
	}
	
	@Test
	public void after_and_equals(){
		MesCobranca mes = new MesCobranca(3,2016);
		
		assertTrue(mes.after(new MesCobranca(2,2016)));
		assertTrue(mes.after(new MesCobranca(3,2015)));
		assertFalse(mes.after(new MesCobranca(4,2016)));
		assertFalse(mes.after(new MesCobranca(3,2017)));
		assertFalse(mes.after(new MesCobranca(3,2016)));
		
		assertTrue(mes.equals(new MesCobranca(3,2016)));
		assertFalse(mes.equals(new MesCobranca(4,2016)));
		assertFalse(mes.equals(new MesCobranca(3,2017)));
		assertFalse(mes.equals(new MesCobranca(4,2017)));
	}
	
	@Test
	public void mesesFromTo_doisMeses() {
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		
		List<MesCobranca> meses = MesCobranca.meses(mes1, mes2);
		assertEquals(2,meses.size());
		assertEquals("3/2016",meses.get(0).toString());
		assertEquals("4/2016",meses.get(1).toString());
	}
	
	@Test
	public void mesesFromTo_tresMeses(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2016);
		mes3.saveIt();
		
		
		List<MesCobranca> meses = MesCobranca.meses(mes1, mes3);
		assertEquals(3,meses.size());
		assertEquals("3/2016",meses.get(0).toString());
		assertEquals("4/2016",meses.get(1).toString());
		assertEquals("5/2016",meses.get(2).toString());
	}
	
	@Test
	public void mesesFromTo_tresMeses_anosDiferentes(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobranca mesNoMeio = new MesCobranca(5,2017);
		mesNoMeio.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2018);
		mes3.saveIt();
		
		
		List<MesCobranca> meses = MesCobranca.meses(mes1, mes3);
		assertEquals(4,meses.size());
		assertEquals("3/2016",meses.get(0).toString());
		assertEquals("4/2017",meses.get(1).toString());
		assertEquals("5/2017",meses.get(2).toString());
		assertEquals("5/2018",meses.get(3).toString());
	}
	
	@Test
	public void primeiroMes(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobranca mesNoMeio = new MesCobranca(5,2017);
		mesNoMeio.saveIt();
		
		MesCobranca primeiroMes = new MesCobranca(6,2015);
		primeiroMes.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2018);
		mes3.saveIt();
	
		MesCobranca mesBuscado = MesCobranca.primeiroMes();
		
		assertEquals("6/2015",mesBuscado.toString());
	}
	
	@Test
	public void ateOMes(){
		MesCobranca primeiroMes = new MesCobranca(6,2015);
		primeiroMes.saveIt();
		
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobranca mesNoMeio = new MesCobranca(5,2017);
		mesNoMeio.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2018);
		mes3.saveIt();
		
		List<MesCobranca> meses = MesCobranca.mesesAte(mesNoMeio);
		
		assertEquals(4,meses.size());
		assertEquals("6/2015",meses.get(0).toString());
		assertEquals("3/2016",meses.get(1).toString());
		assertEquals("4/2017",meses.get(2).toString());
		assertEquals("5/2017",meses.get(3).toString());
	}
	
	@Test
	public void aPartirDoMes(){
		MesCobranca primeiroMes = new MesCobranca(6,2015);
		primeiroMes.saveIt();
		
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobranca mesNoMeio = new MesCobranca(5,2017);
		mesNoMeio.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2018);
		mes3.saveIt();
		
		List<MesCobranca> meses = MesCobranca.mesesAPartirDe(mes2);
		
		assertEquals(3,meses.size());
		assertEquals("4/2017",meses.get(0).toString());
		assertEquals("5/2017",meses.get(1).toString());
		assertEquals("5/2018",meses.get(2).toString());
	}
	
	@Test
	public void compareTo(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		
		MesCobranca mes3 = new MesCobranca(4,2017);
		mes3.saveIt();
		
		MesCobranca mes4 = new MesCobranca(4,2018);
		mes4.saveIt();
		
		assertEquals(0,mes1.compareTo(mes1));
		assertEquals(1, mes2.compareTo(mes1));
		assertEquals(-1,mes1.compareTo(mes2));
		
		assertEquals(-1,mes2.compareTo(mes3));
		assertEquals(-1,mes3.compareTo(mes4));
		assertEquals(1,mes4.compareTo(mes3));
		
	}

}