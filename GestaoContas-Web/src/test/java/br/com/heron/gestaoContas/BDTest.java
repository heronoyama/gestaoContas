package br.com.heron.gestaoContas;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;

//TODO Transformar em hsqldb
public class BDTest {
	@Before
	public void setup(){
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gestaocontas", "root", "");
		
		cleanup();
	}
	
	@After
	public void setDown(){
		cleanup();
		Base.close();
	}
	
	private void cleanup() {
		Base.exec("DELETE FROM despesas_recorrentes");
		Base.exec("ALTER TABLE despesas_recorrentes AUTO_INCREMENT = 1");
		Base.exec("DELETE FROM meses_cobranca");
		Base.exec("ALTER TABLE meses_cobranca AUTO_INCREMENT = 1");
		Base.exec("DELETE FROM cobrancas");
		Base.exec("ALTER TABLE cobrancas AUTO_INCREMENT = 1");
	}
}
