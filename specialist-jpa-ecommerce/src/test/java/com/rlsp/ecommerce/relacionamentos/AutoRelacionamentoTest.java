package com.rlsp.ecommerce.relacionamentos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;

public class AutoRelacionamentoTest extends EntityManagerTest{
	

    @Test
    public void verificarRelacionamento() {
        Categoria categoriaPai = new Categoria();
        categoriaPai.setNome("Eletronicos");
        
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setNome("CellPhones");
        categoriaFilha.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoriaFilha);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaTest = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaTest.getCategoriaPai());

        Categoria categoriaPaiTest = entityManager.find(Categoria.class, categoriapai.getId());
        Assert.assertFalse(categoriaPaiTest.getCategorias().isEmpty());
    }

}
