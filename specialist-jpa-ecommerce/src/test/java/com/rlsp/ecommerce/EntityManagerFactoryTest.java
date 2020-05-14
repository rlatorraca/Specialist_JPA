package com.rlsp.ecommerce;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class EntityManagerFactoryTest {

	protected static EntityManagerFactory entityManagerFactory;
	
	
	 public static void log(Object obj, Object... args) {
	        System.out.println(
	                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
	        );
	  }
	
	/**
	 * Metodos de CallBack do JUNIT
	 *  - usado para instancias a VARIAVEIS acima
	 */
	
	//Executando ANTES das Classes de TODOS Testes INICIAREM
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
	}
	
	//Executando DEPOIS das Classes de TODOS Testes FINALIZAREM
	@AfterClass
	public static void tearDownAfterClass() {
		
		entityManagerFactory.close();
		
	}
}
