package com.rlsp.ecommerce.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe Usada para criar a CHAVE COMPOSTA da Entidade ITEM PEDIDO
 * - deve implementar SERIALIZABLE
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor // Construtor SEM Argumentos
@AllArgsConstructor // Construtor COM TODOS Argumentos
@Embeddable // Mostra que essa Entidade sera INCORPORADA em outra Entidade [Usado para criacao de CHAVES COMPOSTAS)
public class ItemPedidoId implements Serializable{

	private static final long serialVersionUID = 3607522641739186792L;

/**

	@EqualsAndHashCode.Include    
    private Integer pedidoId;
    
    @EqualsAndHashCode.Include    
    private Integer produtoId;
	
*/
	
	
    @EqualsAndHashCode.Include
    //@Id ==> NAO pode ter a anotacao ID para Chave Composta
    //@GeneratedValue(strategy = GenerationType.IDENTITY) ==> NAO USADO (por usar uma CHAVES COMPOSTA PRIMARIA
    @Column(name = "pedido_Id") // pega/usa o mesmo valor do atributo pedido_Id ABAIXO
    private Integer pedidoId;
    
    @EqualsAndHashCode.Include
    //@Id ==> NAO pode ter a anotacao ID para Chave Composta
    //@GeneratedValue(strategy = GenerationType.IDENTITY) ==> NAO USADO (por usar uma CHAVES COMPOSTA PRIMARIA
    @Column(name = "produto_id") // pega/usa o mesmo valor do atributo produto_id ABAIXO
    private Integer produtoId;
	
	

}
