
package com.rlsp.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;



/** Opcao para Chave Composta  
	@IdClass(ItemPedidoId.class) // Mostra onde esta a classe da CHAVE COMPOSTA
*/
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EmbeddedId // INCORPORA a Entidade "ItemPedidoId" ==> CHAVE COMPOSTA
	private ItemPedidoId id;
	
	
/**
    @EqualsAndHashCode.Include
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) ==> NAO USADO (por usar uma CHAVES COMPOSTA PRIMARIA
    @Column(name = "pedido_Id") // pega/usa o mesmo valor do atributo pedido_Id ABAIXO
    private Integer pedidoId;
    
    @EqualsAndHashCode.Include
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) ==> NAO USADO (por usar uma CHAVES COMPOSTA PRIMARIA
    @Column(name = "produto_id") // pega/usa o mesmo valor do atributo produto_id ABAIXO
    private Integer produtoId;
*/
	@MapsId("pedidoId") //temos que pegar o atributo na Entidade "ItemPedidoId"
    @ManyToOne(optional = false)
    //@JoinColumn(name = "pedido_id", insertable = false, updatable = false)
	@JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @MapsId("produtoId") //temos que pegar o atributo na Entidade "ItemPedidoId"
    @ManyToOne(optional = false)
    //@JoinColumn(name = "produto_id" , insertable = false, updatable = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "preco_produto")
    private BigDecimal precoProduto;

    private Integer quantidade;


}
