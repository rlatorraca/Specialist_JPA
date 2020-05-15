
package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

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


@SqlResultSetMapping(name="item_pedido-produto.ItemPedido-Produto",  // Mapeia essa ENTIDADE para pesquisa com SQL (name= "nome+da+tabela".Nome+Entidade)
					 entities= {@EntityResult(entityClass = ItemPedido.class), @EntityResult(entityClass = Produto.class)})

//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/**
 * @EntityListeners ==>  inclui um ARRAY de listeners
 *
 */
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(
            name = "Pedido.dadosEssencias",
            attributeNodes = {
                    @NamedAttributeNode("dataCriacao"),
                    @NamedAttributeNode("status"),
                    @NamedAttributeNode("total"),
                    @NamedAttributeNode(
                            value = "cliente",
                            subgraph = "cli"
                    )
            },
            subgraphs = {
                    @NamedSubgraph(
                            name = "cli",
                            attributeNodes = {
                                    @NamedAttributeNode("nome"),
                                    @NamedAttributeNode("cpf")
                            }
                    )
            }
    )
})


@Getter
@Setter
@Table(name= "pedido")
public class Pedido extends EntidadeBaseInteger {
		//implements PersistentAttributeInterceptable


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
	@NotNull
    @ManyToOne (optional=false)
    @JoinColumn(name="cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente")) //foreing key  = pedido -> cliente
    private Cliente cliente;

    @PastOrPresent // Data de criacao dev ser do PASSADO ou Atual
    @NotNull
    @Column(name="data_criacao",  updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent // Data de criacao dev ser do PASSADO ou Atual
    @Column(name="data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;
    
    @PastOrPresent // Data de criacao dev ser do PASSADO ou Atual
    @Column(name="data_conclusao")
    private LocalDateTime dataConclusao;


    @NotNull
    @Positive // total deve ser > ZERO
    @Column(precision = 10, scale = 2, nullable = false)
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
    //@OneToMany(mappedBy="pedido", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    //@OneToMany(mappedBy="pedido", cascade = CascadeType.REMOVE)    
    
	/**
	 * A propriedade "orphanRemoval" em @OneToMany / @OnetToOne
	 *  - com essa propriedade "true" == CascadeType.REMOVE ==> se removido o pai sera removido os "filhos"
	 *  - possivel excluir 1 item da "List de Itens"
	 *  
	 *  @OneToMany(mappedBy="pedido", cascade = CascadeType.REMOVE, orphanRemoval = true) ==> usado para REMOVER 1 item da lista, e deletando de forma automatica do DB
	 */
    @NotEmpty
    @OneToMany(mappedBy="pedido" , cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ItemPedido> itens;

    
    /**
     * Como a classe Pagamento e uma Entidade Abstrata pode-se fazer referencia
     */
    
	//  @LazyToOne(LazyToOneOption.NO_PROXY)
	    @OneToOne(mappedBy = "pedido")
	//	@OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	    private Pagamento pagamento;
	    
	//  @LazyToOne(LazyToOneOption.NO_PROXY) 
	    @OneToOne(mappedBy = "pedido")
	//    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	    private NotaFiscal notaFiscal;
	    
	//  public NotaFiscal getNotaFiscal() {
	//  if (this.persistentAttributeInterceptor != null) {
	//      return (NotaFiscal) persistentAttributeInterceptor
	//              .readObject(this, "notaFiscal", this. notaFiscal);
	//  }
	//
	//  return this.notaFiscal;
	//}
	//
	//public void setNotaFiscal(NotaFiscal notaFiscal) {
	//  if (this.persistentAttributeInterceptor != null) {
	//      this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor
	//              .writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal);
	//  } else {
	//      this.notaFiscal = notaFiscal;
	//  }
	//}
	//
	//public Pagamento getPagamento() {
	//  if (this.persistentAttributeInterceptor != null) {
	//      return (Pagamento) persistentAttributeInterceptor
	//              .readObject(this, "pagamento", this.pagamento);
	//  }
	//
	//  return this.pagamento;
	//}
	//
	//public void setPagamento(Pagamento pagamento) {
	//  if (this.persistentAttributeInterceptor != null) {
	//      this.pagamento = (Pagamento) persistentAttributeInterceptor
	//              .writeObject(this, "pagamento", this.pagamento, pagamento);
	//  } else {
	//      this.pagamento = pagamento;
	//  }
	//}
	//
	//@Setter(AccessLevel.NONE)
	//@Getter(AccessLevel.NONE)
	//@Transient
	//private PersistentAttributeInterceptor persistentAttributeInterceptor;
	//
	//@Override
	//public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
	//  return this.persistentAttributeInterceptor;
	//}
	//
	//@Override
	//public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
	//  this.persistentAttributeInterceptor = persistentAttributeInterceptor;
	//}
    

    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinals
     */
    @Enumerated(EnumType.STRING)
    @NotNull
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
//            total = itens.stream().map(
//                        i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            total = BigDecimal.ZERO;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public LocalDateTime getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDateTime dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public EnderecoEntregaPedido getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntregaPedido enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	
}
