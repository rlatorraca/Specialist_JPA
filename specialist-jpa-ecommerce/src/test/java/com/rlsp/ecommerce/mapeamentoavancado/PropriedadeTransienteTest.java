package com.rlsp.ecommerce.mapeamentoavancado;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;

public class PropriedadeTransienteTest extends EntityManagerTest {

    @Test
    public void validarPrimeiroNome() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assert.assertEquals("Fernando", cliente.getPrimeiroNome());
    }
}
