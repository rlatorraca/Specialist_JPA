package com.rlsp.ecommerce.datalhesimportantes;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Cliente_;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Pedido_;

/**
 * EntityGraphTest ==> possivel controlar dinanimcante a consulta EAGER ou LAZY
 * @author rlatorraca
 *
 */

public class EntityGraphTest extends EntityManagerTest {
	
	
    @Test
    public void buscarAtributosEssenciaisDePedido() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        /**
         * Os atributos abaixo serao trazidoS, os demais nao especificados serao antoados como lazy (e nao serao trazidos)
         */
        entityGraph.addAttributeNodes(
                "dataCriacao", "status", "total", "notaFiscal");
        
        /**
         * Modos de Utilizacao do EntityGraph
         * 
         * 1) fetchgraph ==> traz os Atributos nominados, os demais nao serao trazidos mesmo que estejam Explicitamente mapeados como Eager (serao Lazy)
         * 
         * 2) laodgraph ==> tragas todos os atributos informados, e os outros atributos devem ser trazidos de acordo com as configuracoes das Entidades
        /*
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        properties.put("javax.persistence.loadgraph", entityGraph);
        Pedido pedido = entityManager.find(Pedido.class, 1, properties);
        Assert.assertNotNull(pedido);
        */

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
    
    
    @Test
    public void buscarAtributosEssenciaisDePedido02() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(
                "dataCriacao", "status", "total");

        /**
         * Subgraph ==> traz alguns atributos das Entidades relacionadas (no caso apenas NOME e CPF de clientes)
         */
        Subgraph<Cliente> subgraphCliente = entityGraph
                .addSubgraph("cliente", Cliente.class);
        subgraphCliente.addAttributeNodes("nome", "cpf");

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    /**
     * Utilziando MetaModel
     */
    @SuppressWarnings("unchecked")
	@Test
    public void buscarAtributosEssenciaisDePedido03() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(
                Pedido_.dataCriacao, Pedido_.status, Pedido_.total);

        Subgraph<Cliente> subgraphCliente = entityGraph
                .addSubgraph(Pedido_.cliente);
        subgraphCliente.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
    
    
    @Test
    public void buscarAtributosEssenciaisComNamedEntityGraph() {
        EntityGraph<?> entityGraph = entityManager
                .createEntityGraph("Pedido.dadosEssencias");
        entityGraph.addAttributeNodes("pagamento");

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    

    
}