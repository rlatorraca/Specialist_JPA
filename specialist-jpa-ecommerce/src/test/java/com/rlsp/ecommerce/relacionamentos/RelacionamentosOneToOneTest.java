package com.rlsp.ecommerce.relacionamentos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.mapeamentoavancado.SalvandoArquivosTest;
import com.rlsp.ecommerce.model.NotaFiscal;
import com.rlsp.ecommerce.model.PagamentoCartao;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPagamento;

public class RelacionamentosOneToOneTest extends EntityManagerTest{
	

    @Test
    public void verificarRelacionamentoPedido_PagamentoCartao() {

    	Pedido pedido = entityManager.find(Pedido.class,1);
    	
    	PagamentoCartao pagamentoCartao = new PagamentoCartao();
    	pagamentoCartao.setNumeroCartao("1234-5678-8965-7845");
    	pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
    	pagamentoCartao.setPedido(pedido);
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(pagamentoCartao);
    	entityManager.getTransaction().commit();;
    	
    	entityManager.clear();
    	
    	Pedido pedidoTest = entityManager.find(Pedido.class, pedido.getId());
    	Assert.assertNotNull(pedidoTest.getPagamento());   
    	
    }	
    
    @Test
    public void verificarRelacionamentoPedido_NotaFiscal() {

    	Pedido pedido = entityManager.find(Pedido.class,1);
    	
    	NotaFiscal nf = new NotaFiscal();
    	nf.setDataEmissao(new Date());
    	nf.setPedido(pedido);
    	nf.setXml(SalvandoArquivosTest.carregarNotaFiscal());
    	//nf.setXml("Xml-embedded");
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(nf);
    	entityManager.getTransaction().commit();;
    	
    	entityManager.clear();
    	
    	Pedido pedidoTest = entityManager.find(Pedido.class, pedido.getId());
    	Assert.assertNotNull(pedidoTest.getNotaFiscal());   
    	
    }	
}
