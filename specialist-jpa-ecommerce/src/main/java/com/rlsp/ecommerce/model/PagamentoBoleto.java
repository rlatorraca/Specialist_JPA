
package com.rlsp.ecommerce.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("boleto") // Para ser usado pela tabela PAI (PAGAMENTO) no campo @DiscriminationColumn (dtype), quando usa SINGLE_TABLE
/**
 * Customizando o nome no DB
 *  - name = nome no DB
 *  - schema = o schema no DB (quando se tem varios schemas / DB diferentes)
 *  - catalog = separar as tabelas em catalagos
 */
//@Table(name= "pagamento_boleto") //Esta notacao sera ignorada quando usada a estrategia SINGLE_TABLE
public class PagamentoBoleto extends Pagamento{

	@NotBlank
	@Column(name="codigo_barra", length = 100)
    private String codigoBarras;    

	@NotNull
    @FutureOrPresent // A data deve ser atual ou no Futuro
	@Column(name="data_vencimento")
	private LocalDate dataVencimento;
}
