package com.rlsp.ecommerce.datalhesimportantes;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;

public class OneLazyTest extends EntityManagerTest {

    @Test
    public void mostrarProblema() {
        System.out.println("BUSCANDO UM PEDIDO:");
        Pedido pedido = entityManager.find(Pedido.class, 1);
        Assert.assertNotNull(pedido);

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        List<Pedido> lista = entityManager
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        Assert.assertFalse(lista.isEmpty());
   
    }
}