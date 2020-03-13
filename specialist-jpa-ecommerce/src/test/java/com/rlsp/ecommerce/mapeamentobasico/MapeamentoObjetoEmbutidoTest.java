package com.rlsp.ecommerce.mapeamentobasico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.EnderecoEntregaPedido;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest{

	
	@Test
	public void analisarMapeamentoObjetoEmbutido() {
	
		EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
		
		endereco.setBairro("Centro");
		endereco.setCep("78045-123");
		endereco.setLogradouro("Rua das Orquideas");
		endereco.setCidade("Arapiraca");
		endereco.setEstado("AL");
		endereco.setNumero("132");
		
		Pedido pedido = new Pedido();
		
		//pedido.setId(1); usando @GeneratedValue(strategy = GenerationType.IDENTITY)
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(new BigDecimal(1230.22));
		pedido.setEnderecoEntrega(endereco);
		
		entityManager.getTransaction().begin();		
		entityManager.persist(pedido);		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidotest = entityManager.find(Pedido.class, pedido.getId());
		
		Assert.assertNotNull(pedidotest);
		
	}

	
}
