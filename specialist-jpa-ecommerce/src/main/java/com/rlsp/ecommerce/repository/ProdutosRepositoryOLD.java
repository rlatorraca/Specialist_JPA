package com.rlsp.ecommerce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.rlsp.ecommerce.model.Produto;

@Repository
public class ProdutosRepositoryOLD {

    @PersistenceContext
    private EntityManager entityManager;

    public Produto buscar(Integer id, String tenant) {
        return entityManager
                .createQuery("select p from Produto p where p.id = :id and p.tenant = :tenant",
                        Produto.class)
                .setParameter("id", id)
                .setParameter("tenant", tenant)
                .getSingleResult();
    }

    public Produto salvar(Produto produto) {
        return entityManager.merge(produto);
    }

    public List<Produto> listar(String tenant) {
        return entityManager
                .createQuery("select p from Produto p where p.tenant = :tenant", Produto.class)
                .setParameter("tenant", tenant)
                .getResultList();
    }
}
