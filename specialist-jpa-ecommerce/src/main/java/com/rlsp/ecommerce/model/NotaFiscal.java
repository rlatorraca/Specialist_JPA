
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger{


    //@GeneratedValue(strategy = GenerationType.IDENTITY)    
	
	/**
	 * Pega a chave PRIMARIA (id) ao estender "EntidadeBaseInteger.class"
	 */
    
//    @EqualsAndHashCode.Include
//    @Id
//    @Column(name = "pedido_id")
//    private Integer id;

    /**
     * @JoinTable funciona tanto para @ManyToMany, quanto @OneToOne
     *  -unique = true ==> serve para garantir 1(um) valor UNICO em cada tabela Pedip vs Nota Fiscal(Sem repeticao nas tabelas)
     */
    //@JoinTable(name = "pedido_nota_fiscal",
    //  joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
    //  inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
    
    /**
     * @MapsId ==> usado para trabalhar CHAVE PRIMARIA que ao mesmo tempo eh CHAVE ESTRANGEIRA
     *  - nao precisa usar em @JoinColumn os atributos "insertable = false", "updatable = false"
     *  - faz uma replicacao / copia da chave Primaria para dentro da chave estrangeira
     */
    @MapsId
    @NotNull
    @OneToOne (optional=false)
	@JoinColumn(name="pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nf_pedido")) //foreing key  = notafiscal -> pedido
    private Pedido pedido;
    
    /**
     * @Lob ==> mostra ao JPA que este atributo pode ser persistido com o ARQUIVO (FILE) de tamanho grande (qualquer coisa que se possa transformar em bytes)
     * 	Lob (Large Object)
     */
    @Lob
    @NotEmpty // nao pode ser vazio
    @Column(nullable = false)
    private byte[] xml;

    @PastOrPresent // A data de ser da emissao ou do pasado
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_emissao", nullable = false)
    private Date dataEmissao;
    
     

}
