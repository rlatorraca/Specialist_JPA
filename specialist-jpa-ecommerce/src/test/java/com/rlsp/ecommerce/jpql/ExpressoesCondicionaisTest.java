package com.rlsp.ecommerce.jpql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class ExpressoesCondicionaisTest extends EntityManagerTest {
    
	@Test
    public void usarExpressaoDiferente() {
        String jpql = "select p from Produto p where p.preco <> 100";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    } 
	
	 @Test
	 public void usarBetween() {
	        String jpql = "select p from Pedido p " +
	                " where p.dataCriacao between :dataInicial and :dataFinal";

	        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
	        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(4));
	        typedQuery.setParameter("dataFinal", LocalDateTime.now());

	        List<Pedido> lista = typedQuery.getResultList();
	        Assert.assertFalse(lista.isEmpty());
	}
	 
    @Test
    public void usarMaiorMenorComDatas() {
        String jpql = "select p from Pedido p where p.dataCriacao > :data";
        

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("data", LocalDateTime.now().minusDays(2));

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor() {
        String jpql = "select p from Produto p  where p.preco >= :precoInicial and p.preco <= :precoFinal";
        //String jpql = "select p from Produto p  where p.preco >= :preco

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
	/**
	 * is nul ==> NAO EXISTE
	 * is not null ==> EXISTE
	 */
    @Test
    public void usarIsNull() {
        String jpql = "select p from Produto p where p.foto is null"; // null = NAO EXISTE na DB (Strings, Numbers, Lob, etc)
        //String jpql = "select p from Produto p where p.foto is not null"; // null = NAO EXISTE na DB (Strings, Numbers, Lob, etc)

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    /**
     * Usando em COLLECTIONS
     * is empty ==> NAO Existe a colecao
     * is not empty ==> EXISTE a colecao
     */
    @Test
    public void usarIsEmpty() {
        String jpql = "select p from Produto p where p.categorias is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

	
	
	/**
	 * Expressoes com uso de LIKE ( = / equal)
	 */
    @Test
    public void usarExpressaoCondicionalLike() {
    	String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";
    	//String jpql = "select c from Cliente c where c.nome like :nome  // ==> procurando EXATAMENTE pelo Nome (como se fosse EQUAL / = )
    	//String jpql = "select c from Cliente c where c.nome like concat('%',nome)  // ==> procurando por algo TERMINANDO pelo Nome (como se fosse EQUAL / = )
    	//String jpql = "select c from Cliente c where c.nome like concat(nome, '%')  // ==> procurando por algo COMECANDO pelo Nome (como se fosse EQUAL / = )

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "a");

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
    
    /**
     * IN
     *  - Serve para quando POSSUIMOS a listagem onde se pretende trabalhar
     *  - Sera paassada um COLLECTION
     */
    @Test
    public void usarExpressaoIN() {
    	
    	//List<Integer> parametros = Arrays.asList(1,3,4);
        
    	Cliente cliente1 = new Cliente(); // entityManager.find(Cliente.class, 1);
        cliente1.setId(1);

        Cliente cliente2 = new Cliente(); // entityManager.find(Cliente.class, 2);
        cliente2.setId(2);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        //String jpql = "select p from Pedido p where p.id in (:param)";
        String jpql = "select p from Pedido p where p.cliente in (:clientes)";

//        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
//        typedQuery.setParameter("param", parametros);
        
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    /**
     * CASE
     *  - criacao de Mensagens usando CASE
     */
    @Test
    public void usarExpressaoCase() {

    	// type() ==> trabalha com a ENTIDADE        
    	String jpql = "select p.id, " +
                " case type(p.pagamento) " +
                "       when PagamentoBoleto then 'Pago com boleto' " +
                "       when PagamentoCartao then 'Pago com cartão' " +
                "       else 'Não pago ainda.' " +
                " end " +
                " from Pedido p";
        
//        String jpql = "select p.id, " +
//                " case p.status " +
//                "       when 'PAGO' then 'Está PAGO' " +
//                "       when 'CANCELADO' then 'Está CANCELADO' " +
//                "       else 'AGUARDANDO ... !!! ' " +
//                " end " +
//                " from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
}

