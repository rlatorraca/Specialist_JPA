package com.rlsp.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.ItemPedido;
import com.rlsp.ecommerce.model.ItemPedido_;
import com.rlsp.ecommerce.model.Pagamento;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Pedido_;
import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.model.StatusPagamento;

public class JoinCriteriaTest extends EntityManagerTest {
	
	@Test
    public void fazerJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
//        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        
        CriteriaQuery<Pagamento> criteriaQuery = criteriaBuilder.createQuery(Pagamento.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        /**
         * root.JOIN
         * 	- nao usa o get()
         * 
         * JPQL
         *  - "select p from Pedido p join p.pagametno pag"
         */
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); // Join ==> retorna apenas valores da tabela PEDIDO que tenham correspeondertes na tabela PAGAMENTO
//	        Join<Pedido, ItemPedido> joinItens = root.join("itens");
//	        Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");
//          Join<Pedio, CLiente> joinItemProduto = joinItens.join("cliente");

//       criteriaQuery.select(root);
	     criteriaQuery.select(joinPagamento);
        /**
         * Usando WHERE
         */
	    criteriaQuery.where(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

//        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        TypedQuery<Pagamento> typedQuery = entityManager.createQuery(criteriaQuery);
//        List<Pedido> lista = typedQuery.getResultList();
        List<Pagamento> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 2);
    }
	
	
	 @Test
	    public void fazerJoinComOn() {
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
	        Root<Pedido> root = criteriaQuery.from(Pedido.class);
	        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
	        joinPagamento.on(criteriaBuilder.equal(
	                joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

	        criteriaQuery.select(root);

	        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
	        List<Pedido> lista = typedQuery.getResultList();
	        Assert.assertTrue(lista.size() == 2);
	    }
	
	

	 /**
	  * Inner Join
	  *  - As informacoes trazidas existem em AMBAS as tabelas conectadas
	  * 
	  * Left Outer join
	  *  - Traz todas a informacoes (as que tem em AMBAS as tabelas  e as informacoes que estao em de referencia (LEFT) e nao batem (nao existe em ambas)]
	  */
	    @Test
	    public void fazerLeftOuterJoin() {
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Pagamento> criteriaQuery = criteriaBuilder.createQuery(Pagamento.class);
	        Root<Pedido> root = criteriaQuery.from(Pedido.class); // a tabela Base ( LEFT )
	        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

	        criteriaQuery.select(joinPagamento);	        

	        TypedQuery<Pagamento> typedQuery = entityManager.createQuery(criteriaQuery);
	        List<Pagamento> lista = typedQuery.getResultList();
	        System.out.println(lista.size());
	        Assert.assertTrue(lista.size() == 5);
	    }
	
	    /**
	     * Fecth
	     *  - para trazer todos os dados das conexoes entre as tabeleas (Entidade Root/Base + Entidas Conectadas ) em APENAS 1 Query
	     */
	@Test
    public void usarJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        root.fetch("itens"); //Inner Join ==> deve ter informacoes que existam/correspondam para ambos os lados {todo PEDIDO tem um CLIENTE)
        root.fetch("notaFiscal", JoinType.LEFT); // Left (outer) ==> joinComo nao existe uma Nota Fiscal para todos os PEDIDOS (SEM CORRESPODENCIA em ambas tabelas)
        root.fetch("pagamento", JoinType.LEFT);  // Left (outer) ==> joinComo nao existe uma Nota Fiscal para todos os PEDIDOS (SEM CORRESPODENCIA em ambas tabelas)
        root.fetch("cliente");
        
        /**
         * FETCH usando Join
         *  - para usar com WHERE
         */
        //Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
    }


	@Test
    public void buscarPedidosComProdutoEspecifico() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<ItemPedido, Produto> joinItemPedidoProduto = root
                //.join("itens")
                //.join("produto");
                .join(Pedido_.itens)         //Com METAMODEL
                .join(ItemPedido_.produto);  //Com METAMODEL 

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(
                joinItemPedidoProduto.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

   
}
