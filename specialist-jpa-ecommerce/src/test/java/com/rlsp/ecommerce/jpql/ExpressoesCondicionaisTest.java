package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

	/**
	 * is nul ==> NAO EXISTE
	 * is not null ==> EXISTE
	 */
    @Test
    public void usarIsNull() {
        String jpql = "select p from Produto p where p.foto is null"; // null = NAO EXISTE na DB (Strings, Numbers, Lob, etc)
        //String jpql = "select p from Produto p where p.foto is not null"; // null = NAO EXISTE na DB (Strings, Numbers, Lob, etc)

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    /**
     * Usando em COLLECTIONS
     * is empty ==> NAO Existe a colecao
     * is not empty ==> EXISTE a colecao
     */
    @Test
    public void usarIsEmpty() {
        String jpql = "select p from Produto p where p.categorias is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

	
	
	/**
	 * Expressoes com uso de LIKE ( = / equal)
	 */
    @Test
    public void usarExpressaoCondicionalLike() {
    	String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";
    	//String jpql = "select c from Cliente c where c.nome like :nome  // ==> procurando EXATAMENTE pelo Nome (como se fosse EQUAL / = )
    	//String jpql = "select c from Cliente c where c.nome like concat('%',nome)  // ==> procurando por algo TERMINANDO pelo Nome (como se fosse EQUAL / = )
    	//String jpql = "select c from Cliente c where c.nome like concat(nome, '%')  // ==> procurando por algo COMECANDO pelo Nome (como se fosse EQUAL / = )

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "a");

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}

