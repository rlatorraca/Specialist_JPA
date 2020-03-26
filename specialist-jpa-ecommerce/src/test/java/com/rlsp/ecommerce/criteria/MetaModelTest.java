package com.rlsp.ecommerce.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.model.Produto_;


public class MetaModelTest extends EntityManagerTest {

	/**
	 * MetaModel
	 * 	- Sao classes que tem os mesmo NOME das ENTIDAS, com informacoes dos Atributos da Entidade
	 *  - Serve para organiza o codigo e evita utlizar strings
	 */
	
	/**
	 * METAMODEL ==> incluidos pela Dependecia HIBERNATE-JPAMODELGEN
	 */
    @Test
    public void utilizarMetaModel() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.like(root.get(Produto_.nome), "%C%"),
                criteriaBuilder.like(root.get(Produto_.descricao), "%C%")
        ));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}