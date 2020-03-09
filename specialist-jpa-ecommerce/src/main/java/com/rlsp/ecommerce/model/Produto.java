package com.rlsp.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Entity = tabela do DB (Base do JPA/Hibernate)
 * @Id = chave primaria (Base do JPA/Hibernate)
 * @Getter e @Setter = do project LOMBOK (Getters, setters, etc)
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	private String nome;
	private String descricao;
	private BigDecimal preco;

	
	
}