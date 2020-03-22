package com.rlsp.ecommerce.jpql;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.dto.ProdutoDTO;
import com.rlsp.ecommerce.model.Cliente;
import com.rlsp.ecommerce.model.Pedido;

public class BasicoJPQLTest extends EntityManagerTest {
	
	/**
	 * JPQL - Java Persistence Query Language
	 *  ** JPQL
	 *  	 - Ex1: select p from Pedido p where p.id =1
	 *  	 - Ex2: select p from Pedido p join p.itens where i.precoProduto > 10 
	 * 		 - Pedido ==> Entidade
	 * 		 - id ==> atributo da Entidade
	 * 
	 * 		- OBS: trará os dados de "Pedido" e das entidades relacionadas 
	 * 
	 *  ** SQL 
	 *      - Ex1: select p.* from Pedido p where p.id =1
	 *      - Ex2: select p from Pedido p join item_pedido i on i.pedido_id = p.id where i.preco_produto > 10
	 * 	    - Pedido ==> Naome da tabela
	 *      - id ==> nome da coluna da tabela
	 *      
	 *      - OBS: trará apenas os dados de "Pedido"
	 */

    @Test
    public void buscarPorIdentificador() {
        // entityManager.find(Pedido.class, 1)

    	/**
    	 * Consulta Tipada
    	 */
        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        /**
         * Para usar typedQuery.getSingleResult(), o resultado DEVE ser 1(hum) , nao pode ser ZERO ou 2+
         */
        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);

        /**
         * Trazendo uma List<> resolver o problema do resultado ser de ZERO ou 2+
         */
//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
    
    /**
     * Diferenca entre TypeQuery x Query (ate JPA 2.0)
     *  - Query precisa faze o "Casting"
     */
    @Test
    public void mostrarDiferencaQueries() {
        String jpql = "select p from Pedido p where p.id = 1";

        System.out.println("-----------TypedQuery--------------");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido1);

        System.out.println("-----------Query--------------");
        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        Assert.assertNotNull(pedido2);

//        List<Pedido> lista = query.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
    
    /**
     * Mostra como pegar ATRIBUTOS especifica de cada Entidade e seu "TIPO DE RETORNO"
     */
    
    @Test
    public void selecionarUmAtributoParaRetorno() {
        String jpql = "select p.nome from Produto p";

        /**
         * Usa-se a "String.class" no retorno pois queremos o "nome"
         */
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();
        Assert.assertTrue(String.class.equals(lista.get(0).getClass()));

        
        /**
         * Usa-se a "Cliente.class" no retorno pois queremos o "cliente"
         */
        String jpqlCliente = "select p.cliente from Pedido p";
        TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> listaClientes = typedQueryCliente.getResultList();
        Assert.assertTrue(Cliente.class.equals(listaClientes.get(0).getClass()));
    }
    
    
    /**
     * Fazendo uma PROJECAO
     * 	 - Signigica retornar DIFERENTES atributos de uma MESMA Entidade
     *   - Usa-se o TIPO DE RETORNO ==> Object[].class (Array de objetos), pois tem Tipos De Retorno DISTINTOS
     */
    @Test
    public void projetarOResultado() {
        String jpql = "select p.id, p.nome from Produto p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertTrue(lista.get(0).length == 2); // Pega o 1º registro da lista e compara para ver se o tamanho de 2 "registros" (id e nome)
        
        System.out.println("----------Projetar Resultado ---------------");
        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
    
    /**
     * Preenchendo / Projetando os dados como DTO e nao Array (como acima)
     */
    @Test
    public void projetraNoDTO() {
    	
    	 String jpql = "select new com.rlsp.ecommerce.dto.ProdutoDTO(id, nome) from Produto p";
    	 
         TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
         List<ProdutoDTO> lista = typedQuery.getResultList();
         
         Assert.assertFalse(lista.isEmpty());
         
         System.out.println("----------Projetar com DTO ---------------");
         lista.forEach(p -> System.out.println(p.getId() + " - " + p.getNome()));
    }
    
    
    
    @Test
    public void ordenarResultados() {
        String jpql = "select c from Cliente c order by c.nome asc"; // desc
        //String jpql = "select c from Cliente c order by c.id desc";
        
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }
    
    /**
     * Evite tuplas duplicadas DISTINCT
     */
    @Test
    public void usarDistinct() {
        String jpql = "select distinct p from Pedido p " +
                " join p.itens i join i.produto pro " +
                " where pro.id in (1, 2, 3, 4) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        System.out.println(lista.size());
    }
    
}
