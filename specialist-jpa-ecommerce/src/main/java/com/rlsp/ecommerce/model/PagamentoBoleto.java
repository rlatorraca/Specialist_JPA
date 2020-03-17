
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
@DiscriminatorValue("boleto") // Para ser usado pela tabela PAI (PAGAMENTO) no campo @DiscriminationColumn (dtype), quando usa SINGLE_TABLE
/**
 * Customizando o nome no DB
 *  - name = nome no DB
 *  - schema = o schema no DB (quando se tem varios schemas / DB diferentes)
 *  - catalog = separar as tabelas em catalagos
 */
@Table(name= "pagamento_boleto") //Esta notacao sera ignorada quando usada a estrategia SINGLE_TABLE
public class PagamentoBoleto extends Pagamento{

	@Column(name="codigo_barra", length = 100)
    private String codigoBarras;    

}
