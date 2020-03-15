package com.rlsp.ecommerce.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 *  ENTIDADE ABSTRATA x @MappedSuperclass
 *  1) Pode-se fazer referencia(relacionamentos) para classes Abstratas e nao para classes que estao apenas anotadas com @MappedSuperclass
 *  
 *  2) A Entidade Abstrata pode ser usada para fazer consultas (Ex: "select p from Pagamento p")
 *  
 *  3) A estrutura da Tabela no DB também ira mudar com as tabelas abstratas
 *  	- A estrategia usada sera do tipo SINGLE TABLES (todos os campos das filhas serao incluidos dentro da tabela da Classe Pai)
 *  	- Sera incluido no DB o tipo DTYPE (serve para diferenciar que tipo de FILHO sera). 
 *  		Ex: no caso sera "Pagamento Cartao" ou "Pagamento Boleto"
 *      - @DiscriminatorColumn(name="dtype") ==> sera usado para mostrar de qual filha pertence a linha
 *      - @Inheritance(strategy = InheritanceType.SINGLE_TABLE) ==> o tipo de estrategia a ser usado
 *  
 *
 */

/**
 * Tipos de Estrategia
 *  1) SINGLE_TABLE ==> uma unica tabela
 *  2) TABLE_PER_CLASS ==> 2 tabelas diferentes (Pagamento_cartao e Pagamento_boleto)
 *  3) JOINED ==> 3 tabelas diferentes (Pagamento, Pagamento_cartao e Pagamento_boleto )
 */

/**
 * Inclui o que eh comum em PagamentoBoleto e PagamentoCartao
 *  
 */
@Getter
@Setter
@DiscriminatorColumn(name="tipo_pagamento", discriminatorType = DiscriminatorType.STRING) // ==> usando apenas com SINGEL TABLE strategy
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "pagamento") //Esta notacao sera ignorada quando usada a estrategia TABLE_PER_CLASS
public abstract class Pagamento extends EntidadeBaseInteger{

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
	/**
     * EnumType.STRING = guarda o NOME e nao valor numeral/ordinal
     */
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
}
