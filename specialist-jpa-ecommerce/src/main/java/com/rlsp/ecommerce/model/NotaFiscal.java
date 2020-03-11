
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "nota_fiscal")
public class NotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="pedido_id")
    private Integer pedidoId;

    private String xml;

    @Column(name="data_emissao")
    private Date dataEmissao;
    
    @OneToOne
	@JoinColumn(name="pedido")
//  @JoinTable(name = "pedido_nota_fiscal",
    //  joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
    //  inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
    private Pedido pedido;

}
