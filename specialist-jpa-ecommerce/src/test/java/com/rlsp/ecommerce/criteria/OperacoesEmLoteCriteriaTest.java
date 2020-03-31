package com.rlsp.ecommerce.criteria;

import java.math.BigDecimal;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Categoria;
import com.rlsp.ecommerce.model.Categoria_;
import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.model.Produto_;

public class OperacoesEmLoteCriteriaTest extends EntityManagerTest {

    @Test
    public void atualizarEmLote() {
    	// String jpql = "update Produto p set p.preco = p.preco + (p.preco * 0.1) where exists (select 1 from p.categorias c2 where c2.id = :categoria)";

        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Produto> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Produto.class);
        Root<Produto> root = criteriaUpdate.from(Produto.class);

        criteriaUpdate.set(root.get(Produto_.preco),
                criteriaBuilder.prod(root.get(Produto_.preco), new BigDecimal("1.1")));

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        Root<Produto> subqueryRoot = subquery.correlate(root);
        Join<Produto, Categoria> joinCategoria = subqueryRoot.join(Produto_.categorias);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(joinCategoria.get(Categoria_.id), 2));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
    
    @Test
    public void removerEmLoteExercicio() {
        entityManager.getTransaction().begin();

//        String jpql = "delete from Produto p where p.id between 5 and 12";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Produto> criteriaDelete = criteriaBuilder.createCriteriaDelete(Produto.class);
        Root<Produto> root = criteriaDelete.from(Produto.class);

        criteriaDelete.where(criteriaBuilder.between(root.get(Produto_.id), 5, 12));

        Query query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
    
    /**
     * NATIVE QUERIES
     * 	a) Porque usar "Native Queries" ?/
     *     1) Por que nao se consegue fazer usando JPA (por desconhecimento ou por a funcionalidade em JPA)
     *     2) Por performance (usar algo nativo do DB pode ser mais rapido)
     *     3) Falta de tempo para migracao
     */
}