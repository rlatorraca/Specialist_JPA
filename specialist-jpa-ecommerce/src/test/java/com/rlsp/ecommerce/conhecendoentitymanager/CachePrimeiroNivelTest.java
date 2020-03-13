package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;
import org.junit.Test;

public class CachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void verificarCache() {
    	
    	/**
    	 * Busca no DB, por meio do find() um objeto que vai ficar GERENCIADO na variavel "produto"
    	 */
        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto.getNome());

        System.out.println("------------------------------");

        entityManager.clear();
//        entityManager.close();
//        entityManager = entityManagerFactory.createEntityManager();

        /**
         * Como ja esta em MEMORIA (em estado GERENCIADO) apenas o JPA busca em memoria
         *  - é o chamado de CACHE DE 1º NIVEL
         */
        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        System.out.println(produtoResgatado.getNome());
    }
}