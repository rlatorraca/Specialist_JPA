package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;
import com.rlsp.ecommerce.model.Produto;
import org.junit.Test;


/**
 * Estado e Ciclos de vida dos Objetos
 * 	1) TRANSIENT / NEW
 *  2) MANAGED
 *  3) DETACHED
 *  4) REMOVED
 *  
 *  ** Persistido (quando gravado no DB) 
 * 		
 * 		1.1) [Instanciacao] ==> Transient = estado NOVO
 * 			- qualquer outro MEIO que nao seja JPA instanciou (como: "new OBJECT() / Entidade"; outro framework)
 * 
 * 	
 * 		2.1) Transient ==> [persist / merge] ==> Managed  = estado GERENCIADO
 * 		2.2) Banco de dados ==> [find, query] ==> Managed = estado GERENCIADO
 * 		2.3) Managed ==> [flush / commit] ==> Banco de Dados = estado PERSISTIDO
 * 		2.4) Managed ==> [remove] ==> Removed = estado REMOVIDO
 * 		2.5) Managed ==> [clear / close] ==> Detached = estado REMOVIDO	 
 *  
 * 	
 * 		4.1) Removed ==> [flush / commit] ==> Banco de Dados = estado PERSISTIDO
 * 		4.2) Removed ==> [persist] ==> Managed = estado REMOVIDO
 * 		4.3) Removed ==> [clear / close] ==> Detached = estado DESANEXADO
 * 
 * 
 */

public class EstadosECiclosDeVidaTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {
        Categoria categoriaNovo = new Categoria(); //estado TRANSIENTE / NOVO

        Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo);  // estado MANAGED

        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1); // estado MANAGED

        entityManager.remove(categoriaGerenciada); //estado REMOVED
        entityManager.persist(categoriaGerenciada); //estdo MANAGED

        entityManager.detach(categoriaGerenciada); //estado DETACHED
    }

}