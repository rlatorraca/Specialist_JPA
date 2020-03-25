package com.rlsp.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.dto.ProdutoDTO;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

/**
 * Criteria API do JPA
 *  - Significa trabalhar chamando METODOS
 *  
 *  a) Vantagens
 *   - Performance igual as @NamedQuery (mesmo montando dinamicamente, no corpo do metodo)), sem necessidade de pre-processamento/parse do JQPL para SQL
 *   
 *   
 *  b) Desvantagens
 *  - Muito mais verbose (mais codigo)
 *
 */

public class BasicoCriteriaTest extends EntityManagerTest {
	
    @Test
    public void buscarPorIdentificador() {
    	/**
    	 *  CriteriaBuilder ==> classe que auxilia na montagem da Query
    	 *  CriteriaQuery<Tipo do Retorno> ==> possue as clausulas do JPQL (Select, groupy, distinct, where , having, order by, etc)
    	 *  Root<Tipo da Raiz> ==> e a Entidade Raiz ( .... from 'Entidade')
    	 */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class); // ... from Pedido p

        
        /**
         * String jpql = "select p from Pedido p where p.id = 1";
         */

        criteriaQuery.select(root); // invoca SELECT e retorna o 'root' (Pedido) que no caso acima eh ' ...from Pedido p'

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // atributo do 'root' (Pedido) 

        
        TypedQuery<Pedido> typedQuery = entityManager
                //.createQuery(jpql, Pedido.class);
                .createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
    }
	
	
    /**
     * Tipo Diferente de retorno
     */
    @Test
    public void selecionarUmAtributoParaRetornoBigDecimal() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root.get("total"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
        BigDecimal total = typedQuery.getSingleResult(); // Retorna somente 1 pedido
        Assert.assertEquals(new BigDecimal("2398.00"), total);
    }
    
    @Test
    public void selecionarUmAtributoParaRetornoCliente() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root.get("cliente"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        Cliente cliente = typedQuery.getSingleResult(); // Retorna somente 1 pedido
        Assert.assertEquals("Fernando Medeiros", cliente.getNome());
    }
	
	
    @Test
    public void retornarTodosOsProdutosExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

	/**
	 * Projecoes com MULTISELECT
	 */
    @Test
    public void projetarOResultado() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        //CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.multiselect(root.get("id"), root.get("nome")); //multiselect ==> para multiplos atributos

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr[0] + ", Nome: " + arr[1]));
    }
	
	/**
	 * Projecoes com TUPLE
	 *  - tuple ==> Arrau de objects ( Objects[] )
	 */
    @Test
    public void projetarOResultadoTuple() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
      //CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .tuple(root.get("id").alias("id_alias"), root.get("nome").alias("nome_alias")));
        
        //criteriaQuery.multiselect(root.get("id"), root.get("nome")); 
        
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(t -> System.out.println("ID: " + t.get("id_alias") + ", Nome: " + t.get("nome_alias")));
        //lista.forEach(t -> System.out.println("ID: " + t.get(0) + ", Nome: " + t.get(1)));
    }
	
	
	
	
	
	

    @Test
    public void projetarOResultadoDTO() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProdutoDTO.class, root.get("id"), root.get("nome")));

        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProdutoDTO> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(dto -> System.out.println("ID: " + dto.getId() + ", Nome: " + dto.getNome()));
    }

  

    

    
   


}
