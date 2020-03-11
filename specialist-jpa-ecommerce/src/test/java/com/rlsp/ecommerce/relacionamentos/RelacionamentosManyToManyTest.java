package com.rlsp.ecommerce.relacionamentos;

import java.util.Arrays;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;
import com.rlsp.ecommerce.model.Produto;

import org.junit.*;

public class RelacionamentosManyToManyTest extends EntityManagerTest{
	

    @Test
    public void verificarRelacionamento() {

    	Produto produto  = entityManager.find(Produto.class, 1);
    	Categoria categoria  = entityManager.find(Categoria.class, 1);
    	
    	
    	entityManager.getTransaction().begin();
    	/**
    	 * ERRADO
    	 *  - O Owner deve ser PRODUTOS e NAO Categoria
    	 */
    	//categoria.setProdutos(Arrays.asList(produto));
    	
    	/**
    	 * CORRETo
    	 *  - O Owner deve ser PRODUTOS e NAO Categoria
    	 */
    	produto.setCategorias(Arrays.asList(categoria));
    	entityManager.getTransaction().commit();;
    	
    	entityManager.clear();
    	
    	Categoria categoriaTest = entityManager.find(Categoria.class, categoria.getId());
    	Assert.assertFalse(categoriaTest.getProdutos().isEmpty());
    
    	
    }	
}
