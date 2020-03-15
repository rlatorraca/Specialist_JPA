
package com.rlsp.ecommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * @SecondaryTable ==> permite ao JPA criar, preencher e trazer valores que estao presentes em uma tabela secundaria
 */
@SecondaryTable(name="cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) ==> usado na classe @MappedSuperclass  = "EntidadeBaseInteger.class"
@Entity
/**
 * - uniqueConstraints = {@UniqueConstraint()} ==> sao um ARRAY de colunas no DB que NAO PODEM se REPETIR (sao UNICAS)
 * - indexes = { @Index(name = "idx_name", columnList = "nome_cliente")} ==> sao indices, e sao usadas para buscar ATRIBUTOS de forma mais eficiente no DB
 * 		** se existirem mais de um atributo no indice de ser feito de ssa forma "columnList = "nome_cliente, sobrenome_cliente" (com virgula e  NAO SERA entre chaves)
 */
@Table(name= "cliente", 
	   uniqueConstraints = {
			   		@UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"})
			   	},
	   indexes = { 
			   		@Index(name = "idx_name", columnList = "nome_cliente")
			   	 }
	)
public class Cliente extends EntidadeBaseInteger{

//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    
	@Column(name = "nome_cliente")
    private String nome;
    
    private String cpf;
    
    /**
     * TRANSIENT
     * 	- é um propriedade do JPA que serve para ele IGNORAR o atributo e nao levar este para dentro do DB (CRIAR, SALVAR, BUSCAR, etc)
     */
    @Transient
    private String primeiroNome;
    
    @Column(name = "sexo", table = "cliente_detalhe") // os valores virao da Tabela Secundaria chamada "cliente_detalhe"
    /**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinal
     */
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;
    
    @Column(name = "data_nascimento", table = "cliente_detalhe") // os valores virao da Tabela Secundaria chamada "cliente_detalhe"
    private LocalDate dataNascimento;
    
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
