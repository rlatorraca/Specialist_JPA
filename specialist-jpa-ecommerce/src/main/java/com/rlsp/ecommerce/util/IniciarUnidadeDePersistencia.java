package com.rlsp.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.rlsp.ecommerce.model.Produto;

/**
 * @EntityManager ==> atraves da instancia dessa interface fa-se todas as operacoes com JPA (pesquisa, alteracao, insercao, delete, etc) 
 *
 */


public class IniciarUnidadeDePersistencia {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//Fazer os testes ou atividades aqui !!!
		Produto produto = entityManager.find(Produto.class, 1);
		System.out.println(produto.getNome().toString());
				
		/**
		 * Depois de utilizar o 'entityManagerFactory' e 'entityManager' dev-se FECHAR / CLOSE()
		 */
		entityManagerFactory.close();
		entityManager.close();
	}
}
