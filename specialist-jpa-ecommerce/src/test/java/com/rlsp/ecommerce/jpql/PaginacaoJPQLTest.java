package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        String jpql = "select c from Categoria c order by c.nome";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);

        // FIRST_RESULT = MAX_RESULTS * (pagina - 1)
        
        System.out.println("---------Pag 1--------------");
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(3);
        List<Categoria> lista = typedQuery.getResultList();
        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
        
        System.out.println("---------Pag 2--------------");
        typedQuery = entityManager.createQuery(jpql, Categoria.class);
        typedQuery.setFirstResult(3);
        typedQuery.setMaxResults(3);
        lista = typedQuery.getResultList();
        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
       
        System.out.println("---------Pag 3--------------");
        typedQuery = entityManager.createQuery(jpql, Categoria.class);
        typedQuery.setFirstResult(6);
        typedQuery.setMaxResults(3);
        lista = typedQuery.getResultList();
        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
        
        System.out.println("---------Pag 4--------------");
        typedQuery = entityManager.createQuery(jpql, Categoria.class);
        typedQuery.setFirstResult(9);
        typedQuery.setMaxResults(3);
        lista = typedQuery.getResultList();
        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
     
        System.out.println("---------Pag 5--------------");
        typedQuery = entityManager.createQuery(jpql, Categoria.class);
        typedQuery.setFirstResult(12);
        typedQuery.setMaxResults(3);
        lista = typedQuery.getResultList();
        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
        
//        List<Categoria> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
//
//        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }
}
