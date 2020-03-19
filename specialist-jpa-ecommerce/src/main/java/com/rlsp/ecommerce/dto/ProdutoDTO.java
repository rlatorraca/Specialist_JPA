package com.rlsp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
	
	private Integer id;
	
	private String nome;

	/**
	 * Necessario o uso de CONSTRUCTOR ou @AllArgsConstructor (do Lombk)
	 */
//	public ProdutoDTO(Integer id, String nome) {
//		super();
//		this.id = id;
//		this.nome = nome;
//	}
	
	

}
