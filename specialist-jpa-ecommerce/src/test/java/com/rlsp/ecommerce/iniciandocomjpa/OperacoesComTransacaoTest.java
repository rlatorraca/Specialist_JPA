package com.rlsp.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;

import org.junit.Assert;

public class OperacoesComTransacaoTest extends EntityManagerTest {
	
	@Test
	public void abrirEFecharATransacao() {
		
		Produto produto = new Produto(); // Criado apenas para evitar / igonar o Erro no 'entityManager'
		
		//Abrindo / Iniciando a transacao
		entityManager.getTransaction().begin(); // ==> sera AUTOMATIZADO (na vida real)
		
		/**
		 * Local de execucao das transacoes (mudancas) no DB
		 */
		//entityManager.persist(produto); // Salva objeto do DB
		//entityManager.merge(produto); //Muda / Salva objeto do DB
		//entityManager.remove(produto); // Removendo objeto do DB
		
		//Fechando / Finaizando a transacao
		entityManager.getTransaction().commit(); // ==> sera AUTOMATIZADO (na vida real)
		
	}
	
	@Test
	public void removerObjeto() {
		
		Produto produto = new Produto();
		produto = entityManager.find(Produto.class, 3);
		
		entityManager.getTransaction().begin();
		
		entityManager.remove(produto); 		
		
		entityManager.getTransaction().commit();
		
		//entityManager.clear(); //Nao eh necessario na remocao limpar a memoria (Removido do DB e removido da MEMORIA tbem)
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 3);
		
		
		Assert.assertNull(produtoVerificacao);
	}
	
	@Test
	public void inserirOPrimeiroObjeto() {
		
		Produto produto = new Produto();
		produto.setId(2);
		produto.setNome("Laptop MAC-X");
		produto.setDescricao("Ultimo MAC-OS de 2020");
		produto.setPreco(new BigDecimal(6755.09));
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produto); // Joga o Obeto na MEMORIA para gerencia-lo
		
		/**
		 * Em tese seria possivel nao usar 'entityManager.getTransaction().begin()' e 'entityManager.getTransaction().commit()' 
		 * e mesmo assim mandar os DADOS para o BD
		 * 	- usando 'entityManager.flush()'
		 *  - Mas havera um ERROR de 'SEM TRANSACAO' 
		 */
		entityManager.getTransaction().commit();
		
		entityManager.clear(); // Limpa a Memoria de dados do EntityManager. Para que o proxima linha possa pegar informacoes do DB e nao da memoria do EntityManager
		
		/**
		 * OU
		 * entityManager.close();
		 * entityManager = entityManagerFactory.createEntityManager();
		 */
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
				
				
	}

}
