package com.rlsp.ecommerce.jpql;

import java.util.List;
import java.util.TimeZone;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;

public class FuncoesTest extends EntityManagerTest {

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
    
    @Test
    public void aplicarFuncaoData() {
    	/**
    	 * O DB esta trabalhando em UTC e o java nao esta (o JVM usa a hora do computador [-4])
    	 * 	- O codigo abaixo deve ser colocando nas classes de configuracao do SPRING / STRUTS, ja que deve ser feito quando a aplicacao iniciar
    	 */
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // Preciso ser colocado na aplicacao para trabalhar igual o DB
        // current_date [Data atual], current_time[hora atual], current_timestamp[data + hora atual]
        // year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao)
        
        
        String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p where hour(p.dataCriacao) > 10";
        //String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p";
        //String jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";
        //String jpql = "select current_date, current_time, current_timestamp from Pedido p";
        		
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }
    
    @Test
    public void aplicarFuncaoNumero() {
    	/**
    	 * abs ==> restorna apenas valores positivos, mesmo que negativo
    	 * mod ==> retorna o resto da divisao
    	 * sqrt ==> raiz quadrada (square root)
    	 */
        String jpql = "select abs(p.total), mod(p.id, 2), sqrt(p.total) from Pedido p where abs(p.total) > 1000";

    	//String jpql = "select abs(-10), mod(3, 2), sqrt(121) from Pedido p";
    	
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }
    
    
    @Test
    public void aplicarFuncaoNativas() {
        
    	String jpql = "select function('dayname', p.dataCriacao) from Pedido p where function('acima_media_faturamento', p.total) = 1";

        //String jpql = "select p from Pedido p where function('acima_media_faturamento', p.total) = 1";
        
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);

        List<String> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        
//        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
//
//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());


        lista.forEach(obj -> System.out.println(obj));
    }

    @Test
    public void aplicarFuncaoColecao() {
    	
    	//retorna com SIZE o tamanho da colecao
        String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 2";

        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);

        List<Integer> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(size -> System.out.println(size));
    }
    
    @Test
    public void aplicarFuncaoAgregacao() {
        // avg [media], count[contar], min[minimo], max[maximo], sum[soma]

    	//String jpql = "select min(p.total) from Pedido p";
    	//String jpql = "select max(p.id) from Pedido p";
    	//String jpql = "select count(p.id) from Pedido p";
        //String jpql = "select avg(p.total) from Pedido p where p.dataCriacao >= current_date";
        String jpql = "select sum(p.total) from Pedido p";

        /**
         * NUMBER ==> aceita qualquer tipo de valores numericos (Integer, float, Bigdecimal)
         */
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(obj));
    }
}

