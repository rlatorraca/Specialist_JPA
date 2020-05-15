
package com.rlsp.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("cartao") // Para ser usado pela tabela PAI (PAGAMENTO) no campo @DiscriminationColumn (dtype) , quando usa SINGLE_TABLE
//@Table(name= "pagamento_cartao") //Esta notacao sera ignorada quando usada a estrategia SINGLE_TABLE
public class PagamentoCartao extends Pagamento{

	@NotBlank
	@Column(name = "numero_cartao", length = 50)
    private String numeroCartao;
    
    

}
