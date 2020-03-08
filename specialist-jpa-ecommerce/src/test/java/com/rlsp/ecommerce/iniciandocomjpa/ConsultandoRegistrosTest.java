package com.rlsp.ecommerce.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;;

public class ConsultandoRegistrosTest extends EntityManagerTest{


	
	/**
	 * BUSCA atraves do identificador (ID)
	 */
	
	@Test
	public void buscaPorId() {
		
		//Passa a CLASSE + ID
		// find() ==> busca as informacoes no DB, diretamente. Nao espera ser invocado alguma propriedade
		Produto produto = entityManager.find(Produto.class, 1);
		
		// getReference () ==> enquanto nao se usa um propriedade (getNome(); getPreco(); etc)  de PRODUTO o Hibernate nao busca nada no DB
		//Produto produto2 = entityManager.getReference(Produto.class, 1);
		
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
