package com.rlsp.ecommerce.operacaoemcascata;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.ItemPedido;
import com.rlsp.ecommerce.model.ItemPedidoId;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class CascadeTypeRemoveTest extends EntityManagerTest {
	
	/**
	 * A propriedade "orphanRemoval" em @OneToMany / @OnetToOne
	 *  - com essa propriedaded "true" = CascadeType.REMOVE
	 */
	@Test
    public void removerItensOrfaos() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        Assert.assertFalse(pedido.getItens().isEmpty());

        entityManager.getTransaction().begin();
        
    	/**
    	 * A propriedade "orphanRemoval" em @OneToMany / @OnetToOne
    	 *  - com essa propriedaded "true" = CascadeType.REMOVE
    	 */
        
        /**
         * remove 1 item (de indice 0) da DB (pois e um Objeto Gerenciado, sera atualizado com o begin() + commit() ) ** Precisa do CascadeTupe.PERSIST
         */
        //pedido.getItens().remove(0);
        pedido.getItens().clear(); // Remove TODOS itens da Collection
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertTrue(pedidoVerificacao.getItens().isEmpty());
    }
	
    //@Test
    public void removerRelacaoProdutoCategoria() {
        Produto produto = entityManager.find(Produto.class, 1);

        Assert.assertFalse(produto.getCategorias().isEmpty()); // Verfica se existe uma tupla (em Categorias

        entityManager.getTransaction().begin();
        produto.getCategorias().clear(); // Limpa as Categorias de Produto (id = 1)
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertTrue(produtoVerificacao.getCategorias().isEmpty());
    }

    @Test
    public void removerPedidoEItens() {
        
    	Pedido pedido = entityManager.find(Pedido.class, 2);

        entityManager.getTransaction().begin();
        System.out.println(pedido);
        entityManager.remove(pedido); // Necessário CascadeType.REMOVE no atributo "itens".
        entityManager.getTransaction().commit();

        entityManager.clear();
        
        System.out.println("DENTRO DO 'removerPedidoEItens' : " + pedido.getId());

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
       // Assert.assertNull(pedidoVerificacao);
    }

    //@Test
    public void removerItemPedidoEPedido() {
        ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(itemPedido); // Necessário CascadeType.REMOVE no atributo "pedido".
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
        Assert.assertNull(pedidoVerificacao);
    }
}
