package com.rlsp.ecommerce.relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;

public class AutoRelacionamentoTest extends EntityManagerTest{
	

    @Test
    public void verificarRelacionamento() {
        Categoria categoriaPai = new Categoria();
        categoriaPai.setNome("Camping");
        
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setNome("Barracas");
        categoriaFilha.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoriaFilha);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaTest = entityManager.find(Categoria.class, categoriaFilha.getId());
        Assert.assertNotNull(categoriaTest.getCategoriaPai());

        Categoria categoriaPaiTest = entityManager.find(Categoria.class, categoriaPai.getId());
        Assert.assertFalse(categoriaPaiTest.getCategorias().isEmpty());
    }

}
