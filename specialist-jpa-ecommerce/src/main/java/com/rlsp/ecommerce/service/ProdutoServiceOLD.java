package com.rlsp.ecommerce.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlsp.ecommerce.model.Produto;
import com.rlsp.ecommerce.repository.ProdutosRepositoryOLD;

@Service
public class ProdutoServiceOLD {

    @Autowired
    private ProdutosRepositoryOLD produtos;

    @Transactional
    public Produto criar(String tenant, Produto produto) {
        //produto.setTenant(tenant);
        produto.setDataCriacao(LocalDateTime.now());

        return produtos.salvar(produto);
    }

    @Transactional
    public Produto atualizar(Integer id, String tenant, Map<String, Object> produto) {
        Produto produtoAtual = produtos.buscar(id, tenant);

        try {
            BeanUtils.populate(produtoAtual, produto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        produtoAtual.setDataUltimaAtualizacao(LocalDateTime.now());

        return produtos.salvar(produtoAtual);
    }
}
