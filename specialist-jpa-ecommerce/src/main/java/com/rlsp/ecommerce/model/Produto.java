package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.rlsp.ecommerce.listener.GenericoListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Entity = tabela do DB (Base do JPA/Hibernate)
 * @Id = chave primaria (Base do JPA/Hibernate)
 * @Getter e @Setter = do project LOMBOK (Getters, setters, etc)
 *
 */

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({ GenericoListener.class })
@Entity
@Table(name= "produto", 
       uniqueConstraints = { 
    		         @UniqueConstraint(name = "unq_nome", columnNames = { "nome_produto" }) 
    		      },
        indexes = { 
        		     @Index(name = "idx_nome", columnList = "nome_produto") }
   )
public class Produto extends EntidadeBaseInteger{

	/**
	 * Pega a chave PRIMARIA (id) ao estender "EntidadeBaseInteger.class"
	 */
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

	@Column(name = "nome_produto", length = 150, nullable = false) // Padrao do JPA ==> nome varchar(255)
    private String nome;

	//@Column(columnDefinition = "varchar(278) not null default 'descricao'") // faz a configuracao do atributo como se fosse um 
	@Lob // Long text
    private String descricao;

	@Column(precision = 10, scale = 2) // 19 numeros ( precision ==> contando os decimais) com 2 casas decimais (scale)
    private BigDecimal preco;
    /**
     * @Column (Detalhes)      
     *  - "updatable = false" : para NAO ser MODIFICADO
     *  - "insertable = false" : NAO SERA valorado na criacao do objeto
     */
    @Column(name="data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

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
	
	
}
