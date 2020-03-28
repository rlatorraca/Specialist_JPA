package com.rlsp.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;

import org.junit.Assert;

public class PaginacaoCriteriaTest extends EntityManagerTest{
	
	@Test
	public void paginarResultados() {
	
		  CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		  
		  CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(Categoria.class);
		  Root<Categoria> root = criteriaQuery.from(Categoria.class);
		  
		  criteriaQuery.select(root); // invoca SELECT e retorna o 'root' (Pedido) que no caso acima eh ' ...from Pedido p'

//		  criteriaQuery.where(criteriaBuilder.equal(root.get(""), 1)); // atributo do 'root' (Pedido) 

		  
		  TypedQuery<Categoria> typedQuery = entityManager.createQuery(criteriaQuery);
		  typedQuery.setFirstResult(6);
		  typedQuery.setMaxResults(2);
		  
		  List<Categoria> lista = typedQuery.getResultList();
		  Assert.assertFalse(lista.isEmpty());
		  lista.forEach(c -> System.out.println(c.getId() + " " + c.getNome()));
	}



}
