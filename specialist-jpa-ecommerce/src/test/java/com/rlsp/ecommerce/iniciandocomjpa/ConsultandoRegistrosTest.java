package com.rlsp.ecommerce.iniciandocomjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rlsp.ecommerce.model.Produto;

import org.junit.Assert;;

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
	
	/**
	 * BUSCA atraves do identificador (ID)
	 */
	
	@Test
	public void buscaPorId() {
		
		//Passa a CLASSE + ID
		// find() ==> busca as informacoes no DB, diretamente. Nao espera ser invocado alguma propriedade
		Produto produto = entityManager.find(Produto.class, 1);
		
		// getReference () ==> enquanto nao se usa um propriedade (getNome(); getPreco(); etc)  de PRODUTO o Hibernate nao busca nada no DB
		Produto produto2 = entityManager.getReference(Produto.class, 1);
		
		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
		
		System.out.println(produto.getPreco());
		
	}
	
	@Test
	public void atualizarReferencia() {
		
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Laptop ASUS"); // troca o valor no DB
		System.out.println(produto.getNome());
		
		
		
		entityManager.refresh(produto); // Faz o REFRESh (Reiniciar) tabela  PRODUTO para os estado ANTERIOR com 'Kindle'
		System.out.println(produto.getNome());
		Assert.assertEquals("Kindle", produto.getNome());
	}
}
