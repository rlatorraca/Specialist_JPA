
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

/**
 * Customizando o nome no DB
 *  - name = nome no DB
 *  - schema = o schema no DB (quando se tem varios schemas / DB diferentes)
 *  - catalog = separar as tabelas em catalagos
 *  - 
 */
@Table(name= "pagamento_boleto")
public class PagamentoBoleto {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name="pedido_id")
    private Integer pedidoId;
    

    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinal
     */
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @Column(name="codigos_barras")
    private String codigoBarras;
    

}
