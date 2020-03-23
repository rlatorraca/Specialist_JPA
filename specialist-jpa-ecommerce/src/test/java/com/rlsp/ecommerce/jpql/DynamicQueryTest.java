package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class DynamicQueryTest extends EntityManagerTest {

	/**
	 * DINAMIC QUERY
	 *  - Quando é possivel montar a QUERY de acordo com a regra passada pelo cliente (se vir o nome, monta com nome, se vier descricao, monta com desricao, etc)
	 *  - Possivel a mudanca, sempre que necessario
	 *  - Nao compilada pela JVM em tempo de compilacao
	 *  
	 * NAME QUERY
	 * 	- Sera compilada na hora da criacao do DB (MELHOR PERFORMANCE)
	 *  - Nunca muda (nao estatica)
	 *  - Colocada na entidade a nomeclartura @NamedQuery
	 */    
    
    /**
     * Exemplo de CONSULTA DINAMICA
     */
    @Test
    public void executarConsultaDinamica() {
    	Produto consultado = new Produto();
    	consultado.setNome("Câmera Go Pro");
    	
    	List<Produto> lista = pesquisar(consultado);
    	
    	Assert.assertFalse(lista.isEmpty());
    	Assert.assertEquals("Câmera GoPro Hero 7'", lista.get(0).getNome());
    }
    
    private List<Produto> pesquisar(Produto consultado){
    
    	StringBuilder jpql = new StringBuilder("select p from Produto p where 1 =1");
    	
    	if(consultado.getNome() != null) {
    		jpql.append(" and p.nome like concat('%', :nome, '%')");
    	}
    	
    	if(consultado.getNome() != null) {
    		jpql.append(" and p.descricao like concat('%', :descricao, '%')");
    	}
    	
    	TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);
    	
    	if(consultado.getNome() != null) {
    		typedQuery.setParameter("nome", consultado.getNome());
    	}
    	
    	if(consultado.getDescricao() != null) {
    		typedQuery.setParameter("nome", consultado.getDescricao());
    	}
    	
		return typedQuery.getResultList();
    	
	}
}
