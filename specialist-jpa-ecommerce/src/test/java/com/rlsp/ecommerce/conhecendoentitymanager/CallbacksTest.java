package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;
import org.junit.Assert;
import org.junit.Test;

/**
 * Callbacks e Eventos do Ciclo de vida
 *  - os eventos sao SALVAR, ATUALIZAR, REMOVER, CARREGAR informacoes do DB
 *  - E para cada evento existira um CALLBACK
 *  
 *  - sao colocados dentros da ENTIDADES
 *  - sao usados para manipular os ATRIBUTOS que estao fortemente ligados a ENTIDADE a que pertence
 *
 */

public class CallbacksTest extends EntityManagerTest {

    @Test
    public void acionarCallbacks() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        entityManager.getTransaction().begin();

        entityManager.persist(pedido);
        entityManager.flush(); //Joga para o DB e chama o o callbacl @PrePresist

        pedido.setStatus(StatusPedido.PAGO); // o callback @PreUpdate
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getDataCriacao());
        Assert.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
    }
}
