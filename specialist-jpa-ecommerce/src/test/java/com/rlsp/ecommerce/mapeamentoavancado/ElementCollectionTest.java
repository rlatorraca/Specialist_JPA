package com.rlsp.ecommerce.mapeamentoavancado;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Especificacao;
import com.rlsp.ecommerce.model.Produto;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void aplicarTags() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class, 1);
        produto.setTags(Arrays.asList("ebook", "livro-digital","leitor-digital", "e-reader"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertFalse(produtoVerificacao.getTags().isEmpty());
    }
    
    @Test
    public void aplicarAcessorios() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class, 1);
        produto.setEspecificacao(Arrays.asList(new Especificacao("Tela", "800x600"), new Especificacao("Tamanho", "12 polegadas")));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertFalse(produtoVerificacao.getEspecificacao().isEmpty());
    }
    
    @Test
    public void aplicarContatos() {
        entityManager.getTransaction().begin();

        Cliente cliente = entityManager.find(Cliente.class, 1);
        cliente.setContatos(Collections.singletonMap("email", "rlsp@rlsp.com.br"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerify = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertEquals("rlsp@rlsp.com.br", clienteVerify.getContatos().get("email"));
    }
}


