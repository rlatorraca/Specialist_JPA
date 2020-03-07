package com.rlsp.ecommerce.iniciandocomjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class ConsultandoRegistrosTest {

	private static EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
	
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
	
	//Executando ANTES das Classes de CADA Testes INICIAR
	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	//Executando DEPOS das Classes de CADA Testes FINALIZAR
	@After
	public void tearDowm() {
		entityManager.close();
	}
	
}
