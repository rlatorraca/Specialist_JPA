
package com.rlsp.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("cartao") // Para ser usado pela tabela PAI (PAGAMENTO) no campo @DiscriminationColumn (dtype) , quando usa SINGLE_TABLE
@Table(name= "pagamento_cartao") //Esta notacao sera ignorada quando usada a estrategia SINGLE_TABLE
public class PagamentoCartao extends Pagamento{

	@Column(name = "numero_cartao")
    private String numeroCartao;
    
    

}
