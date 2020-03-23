package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class SubqueriesTest extends EntityManagerTest {

	/**
	 * SUBQUERIES
	 * - Usado para gerar alguma INFORMACAO que ainda nao se existe dentro do DB
	 * - A subquerie sobe pode RETORNAR apenas 1 resultado (usando uma funcao de agregacao [sum, max, min, avg, etc])
	 */
    @Test
    public void pesquisarSubqueries() {
//      Bons clientes. Versão 2.
       String jpql = "select c from Cliente c where 3000 < (select sum(p.total) from Pedido p where p.cliente = c)";

//      Bons clientes. Versão 1.
//    String jpql = "select c from Cliente c where 3000 < (select sum(p.total) from c.pedidos p)";

//      Todos os pedidos acima da média de vendas
//      String jpql = "select p from Pedido p where p.total > (select avg(total) from Pedido)";

//      O produto ou os produtos mais caros da base.
//      String jpql = "select p from Produto p where p.preco = (select max(preco) from Produto)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }
    
    

    @Test
    public void pesquisarComExists() {
    	/**
    	 * Pode existir NOT EXIST (para verificar a INEXISTENCIA de algum dado) ou EXIST (de forma oposta, verifica a existencia da informacao no DB)
    	 * 
    	 * IN x EXIST
    	 *  - No"IN" precisa de um RETORNO VALIDO
    	 *  - No "EXIST" nao importa o tipo do RETORNO
    	 */
    	
        String jpql = "select p from Produto p where exists " +
                " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p) " +
                " and (p.dataCriacao < current_date)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    @Test
    public void pesquisarComIN() {
    	/**
    	 * Forma 1 - COM DISTINCT
    	 */
    	//String jpql = "select distinct p from Pedido p join p.itens i join i.produto pro where pro.preco > 400";

    	/*
    	 * Forma 2 - COM IN 
    	 *  - o IN precisa retornar algo VALIDO (caso contrario existira um ERRO)
    	 */
    	String jpql = "select p from Pedido p where p.id in ("
    			+ "	select p2.id from ItemPedido i2 "
    			+ "	join i2.pedido p2 "
    			+ "	join i2.produto pro2 "
    			+ "	where pro2.preco > 400)";
        
        //String jpql = "select p from Pedido p where p.id in (4,3,2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
    @Test
    public void pesquisarComINExercicio() {
        String jpql = "select p from Pedido p where p.id in " +
                " (select p2.id from ItemPedido i2 " +
                " join i2.pedido p2 " +
                " join i2.produto pro2 join pro2.categorias c2 where c2.id = 2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
    @Test
    public void perquisarComExistsExercicio() {
    	
    	//Retorna todos os produtos que nao foram vendido ainda
        String jpql = "select p from Produto p " +
                " where exists " +
                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";
       
       

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
   
    @Test
    public void perquisarComSubqueryExercicio() {
    	//Seleciona os cliente que tem 2+ pedidos
        String jpql = "select c from Cliente c where " +
                " (select count(cliente) from Pedido where cliente = c) >= 2";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

   
    @Test
    public void pesquisarComAll() {
        // Todos os produtos não foram vendidos mais depois que encareceram
        String jpql = "select p from Produto p where " +
                " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";

        // Todos os produtos que sempre foram vendidos pelo preco atual.
//        String jpql = "select p from Produto p where " +
//                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
    @Test
    public void pesquisarComAny() {
        /*
         *  Podemos usar o ANY(qualquer) == SOME(algum).
         */

        /**
         * Todos os produtos que já foram vendidos por um preco DIFERENTE do atual
         */
      String jpql = "select p from Produto p " +
                " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

        /**
         *  Todos os produtos que já foram vendidos, PELO MENOS, uma vez pelo preço atual.
         */
//        String jpql = "select p from Produto p " +
//                " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
    
    @Test
    public void pesquisarComAllExercicio() {
        // Todos os produtos que sempre foram vendidos pelo mesmo preço.
        String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
                " ip.precoProduto = ALL " +
                " (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

   
}
