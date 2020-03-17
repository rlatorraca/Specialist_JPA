package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contexto de Persistencia
 *  - Trabalhar com um OBJETO que ja esta PERSISTIDO (ja esta no DB)
 *
 */
public class ContextoDePersistenciaTest extends EntityManagerTest {

    @Test
    public void usarContextoPersistencia() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class, 1); // Coloca no CONTEXTO DE PERSISTENCIA, com find()
        /**
         * Dirty Checking
         * 	- quando faz-se o set() (alterando um valor) em um produto ja no Contexto de Persistencia (Gerenciado)
         *  - é um checana de "sujeira" / alteracoes
         *  - fazendo um UPDATE para DB
         */
        produto.setPreco(new BigDecimal(100.0));

        Produto produto2 = new Produto();
        produto2.setNome("Caneca para café");
        produto2.setPreco(new BigDecimal(10.0));
        produto2.setDescricao("Boa caneca para café");
        produto2.setDataCriacao(LocalDateTime.now());
        entityManager.persist(produto2); // Coloca no CONTEXTO DE PERSISTENCIA, com persist()

        Produto produto3 = new Produto();
        produto3.setNome("Caneca para chá");
        produto3.setPreco(new BigDecimal(10.0));
        produto3.setDescricao("Boa caneca para chá");
        produto3.setDataCriacao(LocalDateTime.now());
        produto3 = entityManager.merge(produto3); // Coloca no CONTEXTO DE PERSISTENCIA, com o merge()

        entityManager.flush();

        /**
         * Dirty Checking
         */
        produto3.setDescricao("Alterar descrição"); // Coloca no CONTEXTO DE PERSISTENCIA, com o update()

        entityManager.getTransaction().commit();
    }
}
