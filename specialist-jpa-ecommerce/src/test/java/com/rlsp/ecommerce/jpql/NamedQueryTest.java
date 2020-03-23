package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class NamedQueryTest extends EntityManagerTest{

    /**
     * Usando XML (arquivo externo) para deixar as JPQLs
     *  - src/main/java
     *  - sera pre-compilada (sera NamedQuery, not a DynamicQuery)
     *  
     *  Location: /META-INF/consultas/produto.xml
     *  
     */
    @Test
    public void executarConsultaArquivoXMLEspecificoProduto() {
        TypedQuery<Produto> typedQuery = entityManager
                .createNamedQuery("Produto.todos", Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }


    /**
     * Usando XML (arquivo externo) para deixar as JPQLs
     *  - src/main/java
     *  - sera pre-compilada (sera NamedQuery, not a DynamicQuery)
     *  Location: /META-INF/consultas/pedidos.xml
     */
    @Test
    public void executarConsultaArquivoXMLEspecificoPedido() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createNamedQuery("Pedido.todos", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    /**
     * Usando XML (arquivo externo) para deixar as JPQLs
     * - src/main/java/resources/META-INF/orm.xml
     * - sera pre-compilada (sera NamedQuery, not a DynamicQuery)
     * 
     */
    @Test
    public void executarConsultaArquivoXML() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createNamedQuery("Pedido.listar", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsulta() {
    	
    	TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.listarPorCategoria", Produto.class);
        typedQuery.setParameter("categoria", 2);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
    
    /**
     * Exemplode DYNAMIC QUERY
     */
    @Test
    public void executarConsultaDinamica() {
    	
    	String jpql = "select p from Produto p";
    	
    	TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        
        typedQuery.setParameter("categoria", 2);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
