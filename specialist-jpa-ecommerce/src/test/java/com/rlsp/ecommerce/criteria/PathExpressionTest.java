package com.rlsp.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente_;
import com.rlsp.ecommerce.model.ItemPedido;
import com.rlsp.ecommerce.model.ItemPedido_;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Pedido_;
import com.rlsp.ecommerce.model.Produto_;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void usarPathExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        /**
         * Path Expression ==> "root.get(Pedido_.cliente).get(Cliente_.nome)" (Pega Cliente dentro de Pedido, e o pega o nome do Cliente dentro de Cliente
         */
        criteriaQuery.where(
                criteriaBuilder.like(root.get(Pedido_.cliente).get(Cliente_.nome), "M%"));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
    
   
    /**
     * Busca os Pedidos que tenha o produto de ID = 1
     *  - Pedido <--> ItemPedido <--> produto = 1
     */
    @Test
    public void buscarPedidosComProdutoDeIDIgual1Exercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.itens);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.equal(
                        joinItemPedido.get(ItemPedido_.produto).get(Produto_.id), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
