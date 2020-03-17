package com.rlsp.ecommerce.mapeamentobasico;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;

public class EstrategiaChavesPrimariaTest extends EntityManagerTest{

	@Test
	public void testarEstrategiaChaves() {
		
		Categoria categoria = new Categoria();
		Categoria categoria2 = new Categoria();
		
		categoria.setNome("Pesca");
		categoria2.setNome("Esportes");
		
		entityManager.getTransaction().begin();		
		entityManager.persist(categoria);
		entityManager.persist(categoria2);	
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria categoriaTest = entityManager.find(Categoria.class, categoria.getId());
		
		Assert.assertNotNull(categoriaTest);
		
	}
}
