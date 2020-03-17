
package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.rlsp.ecommerce.listener.GenericoListener;
import com.rlsp.ecommerce.listener.GerarNotaFiscalListener;

import lombok.Getter;
import lombok.Setter;


/**
 * EAGER vcs LAZY
 *  1) Eager (ancioso) ==>
 *  	- Padrao para atributos que NAO SAO Colecoes 
 *      - Traz a o valor dao atributo no retorno do "entityManager.find(Pedido.class, 1)" ao SER CHAMADO (invocado) e nao quando for ser utilizado
 *      - Ex: Pedido pedido = entityManager.find(Pedido.class, 1); // getCliente() = já preenchido 
 *            pedido.getCliente()
 *      - 
 *  2) Lazy 
 *      - Padrao para COLLECTIONS (Colecoes)
 *      - Owner ou NO Owner
 *      - Traz a Lista QUANDO FOR SER UTILIZADO
 *      - Ex: Pedido pedido = entityManager.find(Pedido.class, 1); 
 *            pedido.getItens().isEmpty() ; // Busca no DB  (itens é um COLECAO / LISTA)
 *     
 */

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/**
 * @EntityListeners ==>  inclui um ARRAY de listeners
 *
 */
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Entity
@Table(name= "pedido")
public class Pedido extends EntidadeBaseInteger{

	/**
	 * Pega a chave PRIMARIA (id) ao estender "EntidadeBaseInteger
	 */
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    
    
    /**
     * Nao precisa de JoinColumn
     * 	OBS  Se nao tiver @joinColumn, sera pega a PK da Entidade de conexao
     * 
     *  (optional = false) ==> Hibernate vai utilizar um INNER JOIN (ao inves do left outer join, que tem menor performance)
     *   - COllections NAO POSSUEM essa opcao
     *   - Obrigatorio o CAMPO /ATRIBUTO na hora do salvar no DB
     */    
	
	// Quando PERSISTIDO em PEDIDO ja sera preenchido os dados de CLIENTE na DB (cascade = CascadeType.PERSIST)
    //@ManyToOne (optional=false, cascade = CascadeType.PERSIST)
    @ManyToOne (optional=false)
    @JoinColumn(name="cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente")) //foreing key  = pedido -> cliente
    private Cliente cliente;

    @Column(name="data_criacao",  updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name="data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    
    @Column(name="data_conclusao")
    private LocalDateTime dataConclusao;


    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal total;
    
    /**
     * fetch = FetchType.LAZY (PADRAO) , busca somente NO MOMENTO que for SER USADO
     * fetch = FetchType.EAGER , busca ao carregar o atributo (Antes de ser usado)
     */
    
    /**
     * Cascade ==> propricia que JPA facam com as entidades conectadas facam operacoes em cascata (quando conectadas)
     *  ** cascade = CascadeType.PERSIST ==> SALVA em "PEDIDO" ira salvar os itens dentro de "ItemPedido"
     *  ** cascade = CascadeType.MERGE ==> ATUALIZA em "PEDIDO" ira salvar os itens dentro de "ItemPedido"
     *  ** cascade = CascadeType.REMOVE ==> REMOVE em "PEDIDO" ira salvar os itens dentro de "ItemPedido"
     *  ** cascade = CascadeType.REFRESH ==> REFRESh em "PEDIDO" ira salvar os itens dentro de "ItemPedido"
     *  
     *  ** cascade = CascadeType.ALL ==> TODOS TIPOS DE CASCADE em "PEDIDO" ira salvar os itens dentro de "ItemPedido"
     *  
     *  - Pode ser usado tanto em @ManyToOne, quanto @OneToMany
     */
    		
    //Quando criado um PEDIDO, sera automaticamente gravada a "Lista de Itens Pedidos"
    //@OneToMany(mappedBy="pedido", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @OneToMany(mappedBy="pedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itens;
    
    /**
     * Como a classe Pagamento e uma Entidade Abstrata pode-se fazer referencia
     */
    
    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;
    
    @OneToOne(mappedBy = "pedido")	
    private NotaFiscal notaFiscal;
    

    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinals
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private StatusPedido status;
    
    /**
     * @Embedded = embute (anexa/conecta) outra classe (dentro da classe Pedido) 
     */
    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;
    
    public boolean isPago() {
    	return StatusPedido.PAGO.equals(status);
    }

    /**
     * METODOS DE CALLBACK 
     *  
     *   OBS: Possivel APENAS 1 Anotacao por classe de cada item abaixo
     *  
     *  - precisa da anotacao @PrePersist, para ser executado como Callback, ANTES do PERSIST
     *  - precisa da anotacao @PreUpdate, para ser executado como Callback, ANTES do UPDATE
     *  - precisa da anotacao @PreRemove, para ser executado como Callback, ANTES de REMOVER
     *       *  
     *  - precisa da anotacao @PosPersist, para ser executado como Callback, APOS do PERSIST
     *  - precisa da anotacao @PosUpdate, para ser executado como Callback, APOS do UPDATE  
     *  - precisa da anotacao @PosRemove, para ser executado como Callback, antes do REMOVE
     *  - precisa da anotacao @PosLoad para ser executado como Callback, APOS do CARREGAR
     *  
     */
    
    @PrePersist // Acionado APENAS na criacao do objeto
    public void aoPersistir() {
    	dataCriacao = LocalDateTime.now();
    	calcularTotal();
    }
    
    
//  @PrePersist
//  @PreUpdate
	 public void calcularTotal() {
	      if (itens != null) {
	          total = itens.stream()
	        		  	.map(ItemPedido::getPrecoProduto)
	        		  	.reduce(BigDecimal.ZERO, BigDecimal::add);
	      }
	 }
    
    
    @PreUpdate // Acionado em TODOS Updates
    public void aoAtualizar() {
    	dataUltimaAtualizacao = LocalDateTime.now();
    	calcularTotal();
    }
    

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir Pedido.");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar Pedido.");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o Pedido.");
    }
    
}
