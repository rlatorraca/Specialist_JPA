
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "nota_fiscal")
public class NotaFIscal {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name="pedido_id")
    private Integer pedidoId;

    private String xml;

    @Column(name="data_emissao")
    private Date dataEmissao;

}
