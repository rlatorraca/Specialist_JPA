package com.rlsp.ecommerce.jpql;//

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;

/**
 * JPQL HYBRID (NamedQuery + DynamicQuery)
 *  - Utilizar DynamicQuery para montar uma NamedQuery
 *  - Cria-se a JQPL no momento da criacao do Entity Manager
 *  - Muito usado quando a JPQL depende de um VARAIAVEL DE AMBIENTE (do S.O.);
 *  - Podeira ser construida nas classes de repositorio do escopo de Aplicacao, no @PostConstruct + @Factory
 */

public class AbordagemHibridaTest extends EntityManagerTest {

    @BeforeClass
    //public static void setUpBeforeClass() {
    public static void condigurarBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");

        EntityManager em = entityManagerFactory.createEntityManager(); // cria um Entity Manager

        /**
         * Keys                          Values

		-->  os.version              :  OS Version  
		-->  os.name                 :  OS Name
		-->  os.arch                 :  OS Architecture	
		-->  java.compiler           :  Name of the compiler you are using
		-->  java.ext.dirs           :  Extension directory path
		-->  java.library.path       :  Paths to search libraries whenever loading
		-->  path.separator          :  Path separator
		-->  file.separator          :  File separator
		-->  user.dir                :  Current working directory of User
		-->  user.name               :  Account name of User
		-->  java.vm.version         :  JVM implementation version
		-->  java.vm.name            :  JVM implementation name
		-->  java.home               :  Java installation directory
		-->  java.runtime.version    :  JVM version
         */
        String variavelPath = System.getProperty("os.name"); // Pega o valor da variavel do sistema
        System.out.println(variavelPath); 
        
        String jpql = "select c from Categoria c";
        TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class); //Cria um DynamicQuery
        
        //typedQuery.setParameter(variavelPath, variavelPath_value);

        entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery); // Transforma em  NamedQuery
    }

    @Test
    public void usarAbordagemHibrida(){
        TypedQuery<Categoria> typedQuery = entityManager
                .createNamedQuery("Categoria.listar", Categoria.class);

        List<Categoria> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}