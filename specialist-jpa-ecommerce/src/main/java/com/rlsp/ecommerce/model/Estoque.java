
package com.rlsp.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "estoque")
public class Estoque extends EntidadeBaseInteger{

	/**
	 * Pega a chave PRIMARIA (id) ao estender "EntidadeBaseInteger.class"
	 */
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    @OneToOne(optional=false)
    @JoinColumn(name="produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_estoque_produto")) //foreing key  = estoque -> produto
    private Produto produto;

    @Column(columnDefinition = "int(11) not null")
    private Integer quantidade;

}
