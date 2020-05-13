package com.rlsp.ecommerce.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @MappedSuperclass
 * 	- semelhante a Heranca em orientacao a objetos
 *  - serve para guardar o que e COMUM nas entidades *
 */


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class EntidadeBaseInteger {

		@EqualsAndHashCode.Include
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
		
		//usado para o controle de CONCORRENCIA
		@Version
		private Integer versao;
}
