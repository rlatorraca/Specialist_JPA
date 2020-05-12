package com.rlsp.ecommerce.datalhesimportantes;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;

public class ValidacaoTest extends EntityManagerTest {

    @Test
    public void validarCliente() {
        entityManager.getTransaction().begin();

        Cliente cliente = new Cliente();
        entityManager.merge(cliente);

        entityManager.getTransaction().commit();
    }
}
