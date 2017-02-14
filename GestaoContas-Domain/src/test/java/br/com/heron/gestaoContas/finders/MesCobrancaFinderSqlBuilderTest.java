package br.com.heron.gestaoContas.finders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.heron.gestaoContas.models.BDTest;
import br.com.heron.gestaoContas.models.MesCobranca;

public class MesCobrancaFinderSqlBuilderTest extends BDTest{

	@Test
	public void mesCobrancaFinderSQL_mesmoAno(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2016);
		mes2.saveIt();
		
		MesCobrancaFinderSqlBuilder builder = new MesCobrancaFinderSqlBuilder(mes1,mes2);
		assertEquals("(mes in (3,4) and ano = 2016)",builder.getSQL());
	}
	
	@Test
	public void mesCobrancaFinderSQL_anosDiferentes_umAnoDiferenca(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobrancaFinderSqlBuilder builder = new MesCobrancaFinderSqlBuilder(mes1,mes2);
		assertEquals("(mes in (3,4,5,6,7,8,9,10,11,12) and ano = 2016) or (mes in (1,2,3,4) and ano = 2017)",builder.getSQL());
	}
	
	@Test
	public void mesCobrancaFinderSQL_anosDiferentes_variosAnosDiferenca(){
		MesCobranca mes1 = new MesCobranca(3,2016);
		mes1.saveIt();
		
		MesCobranca mes2 = new MesCobranca(4,2017);
		mes2.saveIt();
		
		MesCobranca mes3 = new MesCobranca(5,2019);
		mes3.saveIt();
		
		MesCobrancaFinderSqlBuilder builder = new MesCobrancaFinderSqlBuilder(mes1,mes3);
		assertEquals("(mes in (3,4,5,6,7,8,9,10,11,12) and ano = 2016) or (ano in (2017,2018)) or (mes in (1,2,3,4,5) and ano = 2019)",builder.getSQL());
	}

}
