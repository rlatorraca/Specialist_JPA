package com.rlsp.ecommerce.iniciandocomjpa;

import java.math.BigDecimal;

import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Produto;

import org.junit.Assert;

public class OperacoesComTransacaoTest extends EntityManagerTest {
	
	/**
	 * DETACH
	 * 	- Esse metodo faz a DESANEXACAO do "EntityManager" nao permitindo que este faca a INSERCAO, DELECAO ou ATUALIZACAO dos dados no DB
	 *  - Deixa de gerenciar a ENTITY (Entidade)
	 */
	@Test
	public void impedirOperacaoComBancoDeDados() {
		
		Produto produto = entityManager.find(Produto.class, 1);
				
		entityManager.detach(produto); // DESANEXA o OBJETO Gerenciavel do 'EntityManager' 
		
		
		entityManager.getTransaction().begin();
		
		//Nao precisa de 'entityManager.merge(produto);'
		//Percebe que existe um ALTERACAO no atributo ja que pega-se 'produto = entityManager.find(Produto.class, 4);'
		produto.setNome("IBM-Compaq New Generation");
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());

		Assert.assertEquals("Kindle", produtoVerificacao.getNome());
		
		
	}
	
	/**
	 * PERSIST vs MERGE
	 * a) PERSIST
	 * 	- So serve para PERSISTIR, e NAO serve para atualizar
	 *  - Deve ser usado sempre com OBJETO NOVO (Garante a INSERCAO)
	 *  - Coloca na MEMORIA do EntityManager (Objeto Gerenciavel) a instancia criada para manipular os dados(ex: 'Produto produtoPersist;')
	 * 
	 * b) MERGE
	 *  - ATUALIZA e PERSISTIR
	 *  - Faz um COPIA da instancia criada e passa para o EntityManager gerenciar (e ALTERAR), qualqer alteracao posterior nao sera refletida pois,
	 *  	NAO E UM OBJETO GERENCIAVEL	  
	 *  - Deve-se fazer algo assim 'produtoMerge = entityManager.merge(produtoMerge);' para entao poder ser gerenciado o "produtoMerge" (OBJETO GERENCIAVEL)
	 */
	
	@Test
	public void mostrarDiferencaEntrePersistAndMerge() {
		
		Produto produtoPersist = new Produto();
		
		//produtoPersist.setId(6);
		produtoPersist.setNome("Adega UltraClean");
		produtoPersist.setDescricao("Adega italiana de alto nivel de qualidade");
		produtoPersist.setPreco(new BigDecimal(445.29));
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produtoPersist); // Coloca na MEMORIA do entityManager 
		produtoPersist.setNome("Adega UltraClean - 2"); // Possivel ALTERAR por se um OBJETO GERENCIAVEL (na memoria do EntityManager)
		entityManager.getTransaction().commit();
		
		entityManager.clear(); // Limpa a Memoria de dados do EntityManager. Para que o proxima linha possa pegar informacoes do DB e nao da memoria do EntityManager
		

		Produto produtoVerificacao = entityManager.find(Produto.class, produtoPersist.getId());
		Assert.assertNotNull(produtoVerificacao);
				
		System.out.println("------------------------------------------------");
		
		Produto produtoMerge = new Produto();
		
		//produtoMerge.setId(7); 
		produtoMerge.setNome("Refrigerador UtlraSom");
		produtoMerge.setDescricao("Melhor Produto Chines da decada");
		produtoMerge.setPreco(new BigDecimal(4245.29));
		
		entityManager.getTransaction().begin();
		
		produtoMerge = entityManager.merge(produtoMerge); // Alem de ALTERAR o 'merge' faz um INSERCAO no DB
		produtoMerge.setNome("Refrigedar UtlraSom - 2"); // 
		
		entityManager.getTransaction().commit();
		
		entityManager.clear(); // Limpa a Memoria de dados do EntityManager. Para que o proxima linha possa pegar informacoes do DB e nao da memoria do EntityManager
		

		Produto produtoVerificacao2 = entityManager.find(Produto.class, produtoMerge.getId());
		Assert.assertNotNull(produtoVerificacao2);
	}
	
	
	@Test
	public void inserirOPrimeiroObjetoComMerge() {
		
		Produto produto = new Produto();
		
		//produto.setId(5);
		produto.setNome("Camera LiveTech");
		produto.setDescricao("ULTRA HIPER HD IMAGES");
		produto.setPreco(new BigDecimal(1245.21));
		
		entityManager.getTransaction().begin();
		
		entityManager.merge(produto); // Alem de ALTERAR o 'merge' faz um INSERCAO no DB
		
		entityManager.getTransaction().commit();
		
		entityManager.clear(); // Limpa a Memoria de dados do EntityManager. Para que o proxima linha possa pegar informacoes do DB e nao da memoria do EntityManager
		
		/**
		 * OU
		 * entityManager.close();
		 * entityManager = entityManagerFactory.createEntityManager();
		 */
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
				
				
	}

	@Test
	public void atualizarObjetoGerenciado() {
		
		Produto produto = entityManager.find(Produto.class, 4);
				
		
		
		
		entityManager.getTransaction().begin();
		
		//Nao precisa de 'entityManager.merge(produto);'
		//Percebe que existe um ALTERACAO no atributo ja que pega-se 'produto = entityManager.find(Produto.class, 4);'
		produto.setNome("IBM-Compaq New Generation");
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());

		Assert.assertEquals("IBM-Compaq New Generation", produtoVerificacao.getNome());
		
		
	}
	
	@Test
	public void atualizarObjeto() {
		
		Produto produto = new Produto();
		
		produto.setId(4);
		produto.setNome("IBM-Compaq");
		produto.setDescricao("Novo IBM server junto com a Compaq");
		produto.setPreco(new BigDecimal(10500));
		
		entityManager.getTransaction().begin();
		
		entityManager.merge(produto);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
				
		Assert.assertNotNull(produtoVerificacao);
		Assert.assertEquals("IBM-Compaq", produtoVerificacao.getNome());
		
		
	}
	
	@Test
	public void abrirEFecharATransacao() {
		
		Produto produto = new Produto(); // Criado apenas para evitar / igonar o Erro no 'entityManager'
		
		//Abrindo / Iniciando a transacao
		entityManager.getTransaction().begin(); // ==> sera AUTOMATIZADO (na vida real)
		
		/**
		 * Local de execucao das transacoes (mudancas) no DB
		 */
		//entityManager.persist(produto); // Salva um NOVO objeto do DB
		//entityManager.merge(produto); // Atualiza objeto do DB
		//entityManager.remove(produto); // Removendo objeto do DB
		
		//Fechando / Finaizando a transacao
		entityManager.getTransaction().commit(); // ==> sera AUTOMATIZADO (na vida real)
		
		
	}
	
	@Test
	public void removerObjeto() {
		
		Produto produto = new Produto();
		produto = entityManager.find(Produto.class, 3);
		
		entityManager.getTransaction().begin();
		
		entityManager.remove(produto); 		
		
		entityManager.getTransaction().commit();
		
		//entityManager.clear(); //Nao eh necessario na remocao limpar a memoria (Removido do DB e removido da MEMORIA tbem)
		
		Produto produtoVerificacao = entityManager.find(Produto.class, 3);
		
		
		Assert.assertNull(produtoVerificacao);
	}
	
	@Test
	public void inserirOPrimeiroObjeto() {
		
		Produto produto = new Produto();
		//produto.setId(2); usando @GeneratedValue(strategy = GenerationType.IDENTITY)
		produto.setNome("Laptop MAC-X");
		produto.setDescricao("Ultimo MAC-OS de 2020");
		produto.setPreco(new BigDecimal(6755.09));
		
		entityManager.getTransaction().begin();
		
		entityManager.persist(produto); // Joga o Obeto na MEMORIA para gerencia-lo
		
		/**
		 * Em tese seria possivel nao usar 'entityManager.getTransaction().begin()' e 'entityManager.getTransaction().commit()' 
		 * e mesmo assim mandar os DADOS para o BD
		 * 	- usando 'entityManager.flush()'
		 *  - Mas havera um ERROR de 'SEM TRANSACAO' 
		 */
		entityManager.getTransaction().commit();
		
		entityManager.clear(); // Limpa a Memoria de dados do EntityManager. Para que o proxima linha possa pegar informacoes do DB e nao da memoria do EntityManager
		
		/**
		 * OU
		 * entityManager.close();
		 * entityManager = entityManagerFactory.createEntityManager();
		 */
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
				
				
	}
	/**
	 * RELACIONAMENTOS (tipos)
	 * 
	 * 1) Muitos para UM (* -- 1) - @MantyToOne - Mapeamento Bidirecional (Ida e volta)
	 * 		@ManyToOne
	 * 		@joinColumn(name="client_id")
	 * 		privaet Cliente cliente
	 *  
	 * 2) Um para muitos (1 -- *) - @OneToMany - - Mapeamento Bidirecional (Ida e volta)
	 * 		@OneToMany(mappedBy="cliente")
	 * 		private List<Pedido> pedidos
	 * 
	 * 3) Um para Um (1 -- 1) @OneToOne - Mapeamento Bidirecional (Ida e volta)
	 *      @OneToOne
	 *      @JoinColumn(name="nota_fiscal_id"
	 *      privatge NotaFiscal notaFiscal
	 *      
	 *      @OneToOne(mappedBY="notaFiscal")
	 *      private Pedido pedido;
	 * 
	 * 4) Muitos para Muitos (* -- *) @ManyToMany - Mapeamento Bidirecional
	 * 
	 * 		@ManyToMany(name="produto_cateogoria, joinColumns = @JoinColumn(name="produto_id"), inverseJoinColumns = @JoinClumn(name="categoria_id"))
	 * 		private List<Categoria> categorias;
	 * 
	 * 		@ManyToMany(mappedBy="categorias")
	 * 		private List<Produto> produtos;
	 * 
	 *  Owner e Non-Owning
	 *  	- mappedBy = é o Non-Owning (nao dono da relacao)
	 *  	- @JoinTable / @joinColumn = e o Owner (dono da relacao)
	 *  
	 *  	- Quem fazer o JPA persistir a relacao é o OWNER (deve estar preenchido para ser persistido)
	 *  
	 */

}
