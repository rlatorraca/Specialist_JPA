package com.rlsp.ecommerce.mapeamentoavancado;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pagamento;
import com.rlsp.ecommerce.model.PagamentoCartao;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.SexoCliente;
import com.rlsp.ecommerce.model.StatusPagamento;

public class HerancaTest extends EntityManagerTest {

    @Test
    public void salvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Fabiola Morais");
        cliente.setSexo(SexoCliente.FEMININO);
        cliente.setCpf("654");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao.getId());
    }
    

    @Test
    public void buscarPagamentos() {
        @SuppressWarnings("unchecked")
		List<Pagamento> pagamentos = entityManager
                .createQuery("select p from Pagamento p")
                .getResultList();

        Assert.assertFalse(pagamentos.isEmpty());
    }

    @Test
    public void incluirPagamentoPedido() {
        Pedido pedido = entityManager.find(Pedido.class, 3);
        System.out.println(pedido.toString());

        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setPedido(pedido);
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setNumeroCartao("1234-5678-9123-4567");

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getPagamento());
    }
}
