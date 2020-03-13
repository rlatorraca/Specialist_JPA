
package com.rlsp.ecommerce.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "pedido")
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    
    /**
     * Nao precisa de JoinColumn
     * 	OBS  Se nao tiver @joinColumn, sera pega a PK da Entidade de conexao
     * 
     *  (optional = false) ==> Hibernate vai utilizar um INNER JOIN (ao inves do left outer join, que tem menor performance)
     *   - COllections NAO POSSUEM essa opcao
     *   - Obrigatorio o CAMPO /ATRIBUTO na hora do salvar no DB
     */    
    @ManyToOne (optional=false)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @Column(name="data_pedido")
    private LocalDateTime dataPedido;

    @Column(name="data_conclusao")
    private LocalDateTime dataConclusao;


    private BigDecimal total;
    
    /**
     * fetch = FetchType.LAZY (PADRAO) , busca somente NO MOMENTO que for SER USADO
     * fetch = FetchType.EAGER , busca ao carregar o atributo (Antes de ser usado)
     */
    @OneToMany(mappedBy="pedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itensPedido;
    
    @OneToOne(mappedBy = "pedido")	
    private PagamentoCartao pagamento;
    
    @OneToOne(mappedBy = "pedido")	
    private NotaFiscal notaFiscal;
    

    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinal
     */
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    
    /**
     * @Embedded = embute (anexa/conecta) outra classe (dentro da classe Pedido) 
     */
    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

}
