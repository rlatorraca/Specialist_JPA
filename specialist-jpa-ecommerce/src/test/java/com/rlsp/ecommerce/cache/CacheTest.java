package com.rlsp.ecommerce.cache;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rlsp.ecommerce.model.Pedido;

/**
 * CACHES
 * 1) Cache de 1º Nivel
 * 	- é o que se encontra na MEMORIA DO COMPUTADOR da instancia de cada objeto do Entity Manager
 *  - Nao é compartilhado
 * 
 * 2) Cache de 2º Nível (Shared Cache)
 * 	- 
 *
 */

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }


    @Test
    public void buscarDoCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1.find(Pedido.class, 1);

        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);
    }
}