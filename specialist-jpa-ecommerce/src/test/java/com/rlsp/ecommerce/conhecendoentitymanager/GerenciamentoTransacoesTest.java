
package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;
import org.junit.Test;

public class GerenciamentoTransacoesTest extends EntityManagerTest {

    @Test(expected = Exception.class) //Pois estamos esperando um excecao do tipo "Exception.class"
    public void abrirFecharCancelarTransacao() {
    	
    	/**
    	 * Transacoes
    	 * 	- periodo de tempo delimitadas pelo begin() e commit(), fazendo mudancas no DB com consistencia
    	 *  - garantido que as mudancas sejam realizadas por completo ou falhem.
    	 *  - é uma funcionalidade do DB
    	 *  
    	 *  OBS: Consultas (JPQL. find()) nao precisam estar dentro do begin() / commit() pois nao fazerm mudancas no DB
    	 */
        try {

            entityManager.getTransaction().begin();
            metodoDeNegocio();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
        	/**
        	 * Rollback ==> cancelar uma transacao
        	 */
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    private void metodoDeNegocio() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        pedido.setStatus(StatusPedido.PAGO);

        if (pedido.getPagamento() == null) {
            throw new RuntimeException("Pedido ainda não foi pago.");
        } 
    }
}
