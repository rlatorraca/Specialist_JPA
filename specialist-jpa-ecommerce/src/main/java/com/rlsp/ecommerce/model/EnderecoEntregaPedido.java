package com.rlsp.ecommerce.model;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * @Embeddable
 * - essa classde sera embutida em outra classe (no caso Pedido)
 *
 */
@Embeddable
public class EnderecoEntregaPedido {
	
	private String cep;
	
	private String logradouro;
	
	private String complemento;
	
	private String numero;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
	

}
