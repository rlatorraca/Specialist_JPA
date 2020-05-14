package com.rlsp.ecommerce;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;;

public class EntityManagerTest extends EntityManagerFactoryTest {

	protected EntityManager entityManager;

	
	//Executando ANTES das Classes de CADA Testes INICIAR
	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	//Executando DEPOiS das Classes de CADA Testes FINALIZAR
	@After
	public void tearDowm() {
		entityManager.close();
	}
	
	
	
}
