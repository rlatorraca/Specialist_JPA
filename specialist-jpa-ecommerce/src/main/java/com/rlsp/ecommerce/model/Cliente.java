
package com.rlsp.ecommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "cliente")
public class Cliente {

	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    
    
    /**
     * TRANSIENT
     * 	- é um propriedade do JPA que serve para ele IGNORAR o atributo e nao levar este para dentro do DB (CRIAR, SALVAR, BUSCAR, etc)
     */
    @Transient
    private String primeiroNome;
    
    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinal
     */
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;
    
    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedidos;
    
    public void configuraPrimeiroNome() {
    	if(nome != null && !nome.isBlank()) {
    		int index = nome.indexOf(" ");
    		if (index > -1) {
    			primeiroNome = nome.substring(0, index);
    		}
    	}
    }

}
