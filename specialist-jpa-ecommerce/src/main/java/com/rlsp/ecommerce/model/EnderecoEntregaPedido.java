package com.rlsp.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	
	@NotNull
	@Pattern(regexp = "[0-9]{5}-[0-9]{3}")
	@Column(length = 9)
	private String cep;
	
	@NotBlank
	@Column(length = 100)
	private String logradouro;
	
	@Column(length = 10)
	private String complemento;
	
	@NotBlank
	@Column(length = 50)
	private String numero;
	
	@NotBlank
	@Column(length = 50)
	private String bairro;
	
	@NotBlank
	@Column(length = 50)
	private String cidade;
	
	@NotBlank
    @Size(max = 2, min = 2)
	@Column(length = 2)
	private String estado;
	

}
