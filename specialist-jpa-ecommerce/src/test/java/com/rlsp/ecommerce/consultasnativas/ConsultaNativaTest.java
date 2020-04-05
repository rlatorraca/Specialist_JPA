package com.rlsp.ecommerce.consultasnativas;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.dto.CategoriaDTO;
import com.rlsp.ecommerce.dto.ProdutoDTO;
import com.rlsp.ecommerce.model.Categoria;
import com.rlsp.ecommerce.model.ItemPedido;
import com.rlsp.ecommerce.model.Produto;

public class ConsultaNativaTest extends EntityManagerTest {

    @Test
    public void mapearConsultaParaDTOEmArquivoExternoExercicio() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar.dto");

        @SuppressWarnings("unchecked")
        List<CategoriaDTO> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("CategoriaDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarAquivoXML() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar");

        @SuppressWarnings("unchecked")
        List<Categoria> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Categoria => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarUmaNamedNativeQuery02() {
        Query query = entityManager.createNamedQuery("ecm_produto.listar");

        @SuppressWarnings("unchecked")
        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarUmaNamedNativeQuery01() {
        Query query = entityManager.createNamedQuery("produto_loja.listar");

        @SuppressWarnings("unchecked")
        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarColumnResultRetornarDTO() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.ProdutoDTO");

        @SuppressWarnings("unchecked")
        List<ProdutoDTO> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("ProdutoDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarFieldResult() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.Produto");

        @SuppressWarnings("unchecked")
        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void usarSQLResultSetMapping02() {
        String sql = "select ip.*, p.* from item_pedido ip join produto p on p.id = ip.produto_id";

        Query query = entityManager.createNativeQuery(sql,
                "item_pedido-produto.ItemPedido-Produto");

        @SuppressWarnings("unchecked")
        List<Object[]> lista = query.getResultList();

        lista.stream().forEach(arr -> System.out.println(
                String.format("Pedido => ID: %s --- Produto => ID: %s, Nome: %s",
                        ((ItemPedido) arr[0]).getId().getPedidoId(),
                        ((Produto)arr[1]).getId(), ((Produto)arr[1]).getNome())));
    }

    @Test
    public void usarSQLResultSetMapping01() {
        String sql = "select id, nome nome_produto, descricao, data_criacao, data_ultima_atualizacao, preco, foto fotoProduto " +
                " from produto_loja";

        Query query = entityManager.createNativeQuery(sql, "produto_loja.Produto");

        @SuppressWarnings("unchecked")
        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void passarParametros() {
        String sql = "select prd_id id, prd_nome nome_produto, prd_descricao descricao, " +
                "            prd_data_criacao data_criacao, prd_data_ultima_atualizacao data_ultima_atualizacao, " +
                "            prd_preco preco, prd_foto fotoProduto " +
                " from ecm_produto where prd_id = :id";

        Query query = entityManager.createNativeQuery(sql, Produto.class); // Tabela Base
        query.setParameter("id", 201);

        @SuppressWarnings("unchecked")
        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @Test
    public void executarSQLRetornandoEntidade() {
    	/**
    	 * Os campos/atributos devem ter o MESMO nome da tabela base (no caso Produto.class) 
    	 *  ** pode-se buscar qualquer ENtidade desde que respeitado os campos de retorna na tabela base
    	 */
//        String sql = "select id, nome_produto, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
//                " from produto_loja";

//        String sql = "select prd_id id, prd_nome nome_produto, prd_descricao descricao, " +
//                "            prd_data_criacao data_criacao, prd_data_ultima_atualizacao data_ultima_atualizacao, " +
//                "            prd_preco preco, prd_foto foto " +
//                " from ecm_produto";

        String sql = "select id, nome nome_produto, descricao, " +
                "            null data_criacao, null data_ultima_atualizacao, " +
                "            preco, null fotoProduto" +
                " from erp_produto";

        Query query = entityManager.createNativeQuery(sql, Produto.class); // Tabela Base = Produto.class

       
		@SuppressWarnings("unchecked")
		List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void executarSQL() {
    	//String sql = "select * from produto";
        String sql = "select id, nome_produto from produto";
        Query query = entityManager.createNativeQuery(sql);

        
		List<Object[]> lista = query.getResultList();

        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr[0], arr[1])));
    }
}
