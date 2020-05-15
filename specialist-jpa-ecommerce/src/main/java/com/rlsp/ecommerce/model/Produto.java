package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.rlsp.ecommerce.dto.ProdutoDTO;
import com.rlsp.ecommerce.listener.GenericoListener;
import com.rlsp.ecommerce.model.converter.BooleanToSimNaoConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Entity = tabela do DB (Base do JPA/Hibernate)
 * @Id = chave primaria (Base do JPA/Hibernate)
 * @Getter e @Setter = do project LOMBOK (Getters, setters, etc)
 *
 */


//@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@NamedNativeQueries({
    @NamedNativeQuery(name = "produto_loja.listar",
            query = "select id, nome nome_produto, descricao, data_criacao, data_ultima_atualizacao, preco, foto fotoProduto" +
                    " from produto_loja", resultClass = Produto.class),
    @NamedNativeQuery(name = "ecm_produto.listar",
            query = "select * from ecm_produto", resultSetMapping = "ecm_produto.Produto")
})


@SqlResultSetMappings({
	@SqlResultSetMapping(name="produto_loja.Produto",  // Mapeia essa ENTIDADE para pesquisa com SQL (name= "nome+da+tabela".Nome+Entidade)
                    	 entities= {
                    			 @EntityResult(entityClass = Produto.class)
                    	 }),

	@SqlResultSetMapping(name="ecm_produto.Produto",  // Mapeia essa ENTIDADE para pesquisa com SQL (name= "nome+da+tabela".Nome+Entidade)
    					 entities= {
    							 @EntityResult(entityClass = Produto.class, 
	    							fields = {
							    			@FieldResult(name = "id", column = "prd_id"),
							    			@FieldResult(name = "nome", column = "prd_nome"),
							    			@FieldResult(name = "descricao", column = "prd_descricao"),
							    			@FieldResult(name = "preco", column = "prd_preco"),
							    			@FieldResult(name = "fotoProduto", column = "prd_foto"),
							    			@FieldResult(name = "dataCriacao", column = "prd_data_criacao"),
							    			@FieldResult(name = "dataUltimaAtualizacao", column = "prd_data_ultima_atualizacao")
    									})
    					}),
	
	 @SqlResultSetMapping(name = "ecm_produto.ProdutoDTO",
                          classes = {
                        		 @ConstructorResult(targetClass = ProdutoDTO.class,
				                     columns = {
				                             @ColumnResult(name = "prd_id", type = Integer.class),
				                             @ColumnResult(name = "prd_nome", type = String.class)
				                     })
     })
})
//@SqlResultSetMapping(name="item_pedido-produto.ItemPedido-Produto",  // Mapeia essa ENTIDADE para pesquisa com SQL (name= "nome+da+tabela".Nome+Entidade)
//					 entities= {@EntityResult(entityClass = ItemPedido.class), @EntityResult(entityClass = Produto.class)}) 

//@SqlResultSetMapping(name="ecm_produto.Produto",  // Mapeia essa ENTIDADE para pesquisa com SQL (name= "nome+da+tabela".Nome+Entidade)
//				     entities= {@EntityResult(entityClass = Produto.class, 
//				     	fields = {
//				     			@FieldResult(name = "id", column = "prd_id"),
//				     			@FieldResult(name = "nome", column = "prd_nome"),
//				     			@FieldResult(name = "descricao", column = "prd_descricao"),
//				     			@FieldResult(name = "preco", column = "prd_preco"),
//				     			@FieldResult(name = "fotoProduto", column = "prd_foto"),
//				     			@FieldResult(name = "dataCriacao", column = "prd_data_criacao"),
//				     			@FieldResult(name = "dataUltimaAtualizacao", column = "prd_data_ultima_atualizacao"),})
//})

@EntityListeners({ GenericoListener.class })
@Entity
@NamedQueries({	
	@NamedQuery(name ="Produto.listarTodosProdutos", query = "select p from Produto p"),	
	@NamedQuery(name ="Produto.listarPorCategoria", 
	            query = "select p from Produto p where exists (select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)" )
})

@Table(name= "produto", 
       uniqueConstraints = { 
    		         @UniqueConstraint(name = "unq_nome", columnNames = { "nome_produto" }) 
    		      },
        indexes = { 
        		     @Index(name = "idx_nome", columnList = "nome_produto") }
)
@Getter
@Setter
public class Produto extends EntidadeBaseInteger{

	/**
	 * Pega a chave PRIMARIA (id) ao estender "EntidadeBaseInteger.class"
	 */
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

	@NotBlank
	@Column(name = "nome_produto", length = 150, nullable = false) // Padrao do JPA ==> nome varchar(255)
    private String nome;

	//@Column(columnDefinition = "varchar(278) not null default 'descricao'") // faz a configuracao do atributo como se fosse um 
	@Lob // Long text
    private String descricao;

	@Positive //preco deve ser > ZERO
	@Column(precision = 10, scale = 2) // 19 numeros ( precision ==> contando os decimais) com 2 casas decimais (scale)
    private BigDecimal preco;
    
	/**
     * @Column (Detalhes)      
     *  - "updatable = false" : para NAO ser MODIFICADO
     *  - "insertable = false" : NAO SERA valorado na criacao do objeto
     */
	@PastOrPresent
    @NotNull
    @Column(name="data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

	@PastOrPresent   
    @Column(name="data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    
    
    //@ManyToMany(cascade = CascadeType.PERSIST)
    //@ManyToMany(cascade = CascadeType.MERGE)
    @ManyToMany
    @JoinTable(name="produto_categoria", 
        		//Coluna da tabela "produtos_categorias" (tabela join) que referencia o id da tabela Produto
    			joinColumns = @JoinColumn(name="produto_id" , nullable = false, 
    					foreignKey = @ForeignKey(name = "fk_produto_produtocategoria")),//foreing key  = produto -> produto_categoria
    			inverseJoinColumns = @JoinColumn (name ="categoria_id" , nullable = false, 
    					foreignKey = @ForeignKey(name = "fk_produtocategoria_produto")) //Coluna da tabela "produtos_categorias" (tabela join) que referencia o id da tabela Categoria
               )     			
    private List<Categoria> categorias;
    
    @OneToOne(mappedBy = "produto")
    private Estoque estoque;
    
    /**
     *  @ElementCollection
     *  	- serve para gerenciar a COLLECTION (de tipo basico abaixo) como se fosse um Entidade (Ex: List<Categoria> cateogoria)
     *      - Cria uma OUTRA/NOVA TABELA para guardar a Collection de tags
     *      @CollectionTable ==> serve para customizar a tabela que sera criada
     *      	joinColumns = @JoinColumn(name="produto_id") ==> sera um coluna da tabela criada [ Conectara com a tabela PRODUTO no caso em tela]
     *      		@JoinColumn(name="produto_id") ==> faz referencia para tabela PRODUTO
     *      @Column(name = "tag_name") ==> serve para customizar a Coluna com as TAGs
     */
    @ElementCollection    
    @CollectionTable(name="produto_tag"
    				, joinColumns = @JoinColumn(name="produto_id"))
    @Column(name = "tag_name", length = 50, nullable = false)
    private List<String> tags; //==> Lista de ELEMENTOS BÁSICOS
    
    
    @ElementCollection    
    @CollectionTable(name="produto_espeficacao"
    		, joinColumns = @JoinColumn(name="produto_id"))
    private List<Especificacao> especificacao; //==> Lista de OBJETOS EMBUTIDOS
    
    /**
     * @Lob ==> mostra ao JPA que este atributo pode ser persistido com o ARQUIVO (FILE) de tamanho grande (qualquer coisa que se possa transformar em bytes)
     * 	Lob (Large Object)
     */
    @Lob
    private byte[] fotoProduto;
	
    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(length = 3, nullable = false)
    private Boolean ativo = Boolean.FALSE;

	
    
	
}
