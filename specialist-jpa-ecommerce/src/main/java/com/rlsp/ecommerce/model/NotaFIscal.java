
package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class NotaFIscal {

	@EqualsAndHashCode.Include
    @Id
    private Integer id;

    private LocalDateTime dataPedido;
    
    private LocalDateTime dataConclusao;
    
    private Integer notaFiscalId;
    
    private BigDecimal total;
    
    private StatusPedido status;

}
