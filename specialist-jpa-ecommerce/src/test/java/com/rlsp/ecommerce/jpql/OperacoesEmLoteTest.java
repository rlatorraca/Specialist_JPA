package com.rlsp.ecommerce.jpql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;

/**
 * OPERACOES DE INSERCAO EM LOTE EM JPA
 *
 */
public class OperacoesEmLoteTest extends EntityManagerTest {

	/**
	 * Serve para o GERENCIAMENTO DE MEMORIA
	 */
    private static final int LIMITE_INSERCOES = 4;

    @Test
    public void inserirEmLote() throws IOException {
    	
    	//Lvvante o Arquivo
        InputStream in = OperacoesEmLoteTest.class.getClassLoader()
                .getResourceAsStream("produtos/importar.txt");

        //Lendo o arquivo
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        
        entityManager.getTransaction().begin();

        int contadorInsercoes = 0;

        //Lendo linha a linha e inserindos dados
        for(String linha: reader.lines().collect(Collectors.toList())) {
            if (linha.isBlank()) {
                continue; // aquela linha eh ignorada e busca a proxima linha (vai pra proxima iteracao)
            }

            String[] produtoColuna = linha.split(";");
            Produto produto = new Produto();
            produto.setNome(produtoColuna[0]);
            produto.setDescricao(produtoColuna[1]);
            produto.setPreco(new BigDecimal(produtoColuna[2]));
            produto.setDataCriacao(LocalDateTime.now());

            entityManager.persist(produto);

            if (++contadorInsercoes == LIMITE_INSERCOES) {
                entityManager.flush(); // Manda pro Banco 
                entityManager.clear(); // Limpa a memoria

                contadorInsercoes = 0; // Zera o contador

                System.out.println("---------------------------------");
            }
        }

        entityManager.getTransaction().commit();
    }
    
    
    @Test
    public void atualizarEmLote() {
        entityManager.getTransaction().begin();

        //String jpql = "update Produto p set p.preco = p.preco + 1 where id betwween 1 and 10";
        String jpql = "update Produto p set p.preco = p.preco + (p.preco * 0.1) " +
                " where exists (select 1 from p.categorias c2 where c2.id = :categoria)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("categoria", 2);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
    
    @Test
    public void removerEmLote() {
        entityManager.getTransaction().begin();

        String jpql = "delete from Produto p where p.id between 8 and 12";

        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }
}