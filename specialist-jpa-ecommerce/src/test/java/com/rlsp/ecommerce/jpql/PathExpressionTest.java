package com.rlsp.ecommerce.jpql;

import com.rlsp.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

	/**
	 * PATH EXPRESSION
	 *  - E percorrer um caminho ate chegar em uma determinada PROPRIEDADE
	 *  - Ex: Para chegar em nome, preciso passar antes em Cliente e antes em Pedido
	 *        "p.cliente.nome" 
	 */
    @Test
    public void usarPathExpressions() {
        String jpql = "select p.cliente.nome from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
    
    @Test
    public void buscarPedidoComProdutoEspecifico() {
              
        
        String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
//      String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
//      String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
        

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
