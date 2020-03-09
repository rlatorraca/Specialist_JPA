package com.rlsp.ecommerce.relacionamentos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;

import org.junit.*;

public class RelacionamentosManyToOneTest extends EntityManagerTest{
	
	@Test
	public void verificarRelacionamento() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(BigDecimal.TEN);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoTest = entityManager.find(Pedido.class, pedido.getId());
		
		
		Assert.assertNotNull(pedidoTest);
		
	}

}
