package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;

public class FuncoesStringsTest extends EntityManagerTest {

    @Test
    public void aplicarFuncao() {
        // concat, length, locate, substring, lower, upper, trim

        String jpql = "select c.nome, length(c.nome) from Categoria c where substring(c.nome, 1, 1) = 'F'";
        // String jpql = "select c.nome, length(c.nome) from Categoria c where length(c.nome) > 10"; 
        //String jpql = "select c.nome, concat('Categoria : ' , c.nome) from Categoria c";
    	//String jpql = "select c.nome, concat('Tamanho do Nome : ' ,length(c.nome)) from Categoria c ";
    	//String jpql = "select c.nome, concat('Quantas letras A : ' , locate('a',c.nome)) from Categoria c "; // como INDEXOF java, retorna em que posicao foi achada a 1ª letra 'A'
    	//String jpql = "select c.nome, concat('Iniciais Secao[1 - 3] : ' , substring(c.nome, 1 ,3)) from Categoria c ";  //1 == inicio 3 = pega as 3 letras a partir da 1ª
    	//String jpql = "select c.nome, concat('Categoria : ' , c.nome) from Categoria c";
    	//String jpql = "select upper(c.nome), concat('Categoria : ' , lower(c.nome)) from Categoria c";
        
    	TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }
}
