
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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


    //@GeneratedValue(strategy = GenerationType.IDENTITY)    
    
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "pedido_id")
    private Integer id;

    /**
     * @JoinTable funciona tanto para @ManyToMany, quanto @OneToOne
     *  -unique = true ==> serve para garantir 1(um) valor UNICO em cada tabela Pedip vs Nota Fiscal(Sem repeticao nas tabelas)
     */
    //@JoinTable(name = "pedido_nota_fiscal",
    //  joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
    //  inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
    
    /**
     * @MapsId ==> usado para trabalhar Chave Primaria que ao mesmo tempo eh CHAVES ESTRANGEIRA
     *  - nao precisa usar em @JoinColumn os atributos "insertable = false", "updatable = false"
     */
    @MapsId
    @OneToOne (optional=false)
	@JoinColumn(name="pedido_id")
    private Pedido pedido;
    
    private String xml;

    @Column(name="data_emissao")
    private Date dataEmissao;
    
     

}
