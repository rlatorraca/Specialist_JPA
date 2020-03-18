package com.rlsp.ecommerce.jpql;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;

public class BasicoJPQLTest extends EntityManagerTest {
	
	/**
	 * JPQL - Java Persistence Query Language
	 *  ** JPQL
	 *  	 - Ex1: select p from Pedido p where p.id =1
	 *  	 - Ex2: select p from Pedido p join p.itens where i.precoProduto > 10 
	 * 		 - Pedido ==> Entidade
	 * 		 - id ==> atributo da Entidade
	 * 
	 * 		- OBS: trará os dados de "Pedido" e das entidades relacionadas 
	 * 
	 *  ** SQL 
	 *      - Ex1: select p.* from Pedido p where p.id =1
	 *      - Ex2: select p from Pedido p join item_pedido i on i.pedido_id = p.id where i.preco_produto > 10
	 * 	    - Pedido ==> Naome da tabela
	 *      - id ==> nome da coluna da tabela
	 *      
	 *      - OBS: trará apenas os dados de "Pedido"
	 */

    @Test
    public void buscarPorIdentificador() {
        // entityManager.find(Pedido.class, 1)

    	/**
    	 * Consulta Tipada
    	 */
        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        /**
         * Para usar typedQuery.getSingleResult(), o resultado DEVE ser 1(hum) , nao pode ser ZERO ou 2+
         */
        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);

        /**
         * Trazendo uma List<> resolver o problema do resultado ser de ZERO ou 2+
         */
//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
    
    /**
     * Diferenca entre TypeQuery x Query (ate JPA 2.0)
     *  - Query precisa faze o "Casting"
     */
    @Test
    public void mostrarDiferencaQueries() {
        String jpql = "select p from Pedido p where p.id = 1";

        System.out.println("-----------TypedQuery--------------");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido1);

        System.out.println("-----------Query--------------");
        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        Assert.assertNotNull(pedido2);

//        List<Pedido> lista = query.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
}
