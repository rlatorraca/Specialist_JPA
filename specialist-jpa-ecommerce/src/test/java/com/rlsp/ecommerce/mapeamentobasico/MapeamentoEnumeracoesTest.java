package com.rlsp.ecommerce.mapeamentobasico;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.SexoCliente;

import  org.junit.Assert;

public class MapeamentoEnumeracoesTest extends EntityManagerTest{

	@Test
	public void testarEnum() {
		
		Cliente cliente = new Cliente();
		
		//cliente.setId(5); usando @GeneratedValue(strategy = GenerationType.IDENTITY)
		cliente.setNome("Gabriel da Silva");
		cliente.setSexo(SexoCliente.MASCULINO);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		
		Assert.assertNotNull(clienteVerificacao);
		
	}
}
