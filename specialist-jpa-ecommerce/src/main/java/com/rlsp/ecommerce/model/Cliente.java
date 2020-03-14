
package com.rlsp.ecommerce.model;

import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
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
    
    @ElementCollection // Cria outra TABELA pelo JPA
    @CollectionTable(name = "cliente_contato",
    				joinColumns = @JoinColumn(name = "cliente_id"))
    @MapKeyColumn(name = "tipo_do_contato") //O Nome da KEY (do Map<Key,value>) dento do da tabela
    @Column (name = "valor_do_contato") // //O Nome da VALUE (do Map<Key,value>) dento do da tabela
    private Map<String, String> contatos;
    
    public void configuraPrimeiroNome() {
    	if(nome != null && !nome.isBlank()) {
    		int index = nome.indexOf(" ");
    		if (index > -1) {
    			primeiroNome = nome.substring(0, index);
    		}
    	}
    }
    

}
