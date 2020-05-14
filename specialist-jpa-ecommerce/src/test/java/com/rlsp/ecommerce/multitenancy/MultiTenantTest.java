package com.rlsp.ecommerce.multitenancy;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerFactoryTest;
import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.multitenancy.EcmCurrentTenantIdentifierResolver;

public class MultiTenantTest extends EntityManagerFactoryTest {

    @Test
    public void usarAbordagemPorMaquina() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("rlsp_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Produto produto1 = entityManager1.find(Produto.class, 1);
        Assert.assertEquals("Kindle", produto1.getNome());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("rlsp2_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Produto produto2 = entityManager2.find(Produto.class, 1);
        Assert.assertEquals("Kindle Paperwhite", produto2.getNome());
        entityManager2.close();
    }

    @Test
    public void usarAbordagemPorSchema() {
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("rlsp_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Produto produto1 = entityManager1.find(Produto.class, 1);
        Assert.assertEquals("Kindle", produto1.getNome());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("rlsp2_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Produto produto2 = entityManager2.find(Produto.class, 1);
        Assert.assertEquals("Kindle Paperwhite", produto2.getNome());
        entityManager2.close();
    }
}