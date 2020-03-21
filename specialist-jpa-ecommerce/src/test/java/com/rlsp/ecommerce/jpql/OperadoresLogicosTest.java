package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;

public class OperadoresLogicosTest extends EntityManagerTest {

    @Test
    public void usarOperadores() {
        String jpql = "select p from Pedido p where (p.status = 'AGUARDANDO' or p.status = 'PAGO') and p.total > 100";
        //String jpql = "select p from Pedido p where p.status = 'AGUARDANDO' or p.status = 'PAGO'";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
   
    }
}    