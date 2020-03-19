package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.google.protobuf.Internal.ListAdapter;
import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;

public class JoinTest extends EntityManagerTest{


    /**
     * JOIN com JPQL
     *  - como temos as Entidades como relacionamentos mapeados( dentro do MODEL = @ManyToMany / @OneToMany @ManyToOne) fica usar o JOIN or INNER JOIN
     */
    @Test
    public void fazerJoin() {
    	
    	/**
    	 *  inner join ==> traz os resultados que existam em AMBAS as tabelas
    	 */
    	 String jpql = "select p from Pedido p inner join p.pagamento pag";

         TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
         List<Pedido> lista = typedQuery.getResultList();

         Assert.assertTrue(lista.size() == 2); 
    }
    
    @Test
    public void fazerJoinList() {
    	
    	/**
    	 *  inner join ==> traz os resultados que existam em AMBAS as tabelas
    	 */
    	 //String jpql = "select p, pag from Pedido p inner join p.pagamento pag where pag.status='RECEBIDO'";
    	 //String jpql = "select p.id, p.cliente.nome, i.precoProduto from Pedido p inner join p.itens i where i.precoProduto < 100";
    	 //String jpql = "select p.id, i.precoProduto , pag.status from Pedido p inner join p.itens i join p.pagamento pag";
    	 String jpql = "select p.id, i.precoProduto , pag.status from Pedido p inner join p.itens i join p.pagamento pag where pag.status = 'RECEBIDO'";

         TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
         List<Object[]> lista = typedQuery.getResultList();

         lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2]));
         Assert.assertFalse(lista.isEmpty()); 
    }
    
    
    /**
     * (Inner) Join x Left (outer) Join
     *  - left(esquerda) PRIMEIRO traz todas as tuplas da tabela que esta a ESQUERDA (tabela Base) que tenha relacao com com JOIN e DEPOIS pega todas as tuplas que 
     *     NAO TENHAM relacioanamento com tabela
     *  - 
     */
    @Test
    public void fazerLeftJoin() {
        String jpql = "select p.id, pag.status from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        
        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        Assert.assertFalse(lista.isEmpty());
    }
    
    
    /**
     * FETCH (buscar)
     *  - busca algo a mais do que seria retornado
     *  - usado para trazer mais informacoes das TABELAS com RELACIONAMENTO e as Entidades
     *  - Pode ser usado com (inner) JOIN e LEFT (outer) JOIN
     *  
     */
    @Test
    public void usarJoinFetch() {
    	/**
    	 *  Traz os dados de PEDIDO + dados da tabela PAGAMENTO e NOTAFISCAL
    	 *  
    	 *  - Resolve o Problema do N+1, faz varios SELECTS em vez de apenas 1 (quando se usa o FETCH)
    	 */
        String jpql = "select p from Pedido p "
                 + " left join fetch p.pagamento " // usando left join traz todos pedidos (com ou sem conexao)
                 + " join fetch p.cliente "        // usando join traz APENAS os clientes que tenham conexao
                 + " left join fetch p.notaFiscal ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
