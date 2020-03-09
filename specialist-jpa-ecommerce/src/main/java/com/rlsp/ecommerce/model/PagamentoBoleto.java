
package com.rlsp.ecommerce.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PagamentoBoleto {

	@EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer pedidoId;
    
    private Date dataEmissao;
    
    private String xml;
    
    

}
