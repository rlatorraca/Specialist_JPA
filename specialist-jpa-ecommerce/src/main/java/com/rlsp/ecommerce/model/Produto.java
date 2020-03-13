package com.rlsp.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name= "produto")
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal preco;
    
    
    @ManyToMany
    @JoinTable(name="produtos_categorias", 
    			joinColumns = @JoinColumn(name="produto_id"), //Coluna da tabela "produtos_categorias" (tabela join) que referencia o id da tabela Produto
    			inverseJoinColumns = @JoinColumn (name ="categoria_id")) //Coluna da tabela "produtos_categorias" (tabela join) que referencia o id da tabela Categoria    			
    private List<Categoria> categorias;
    
    @OneToOne(mappedBy = "produto")
    private Estoque estoque;
    

	
	
}
