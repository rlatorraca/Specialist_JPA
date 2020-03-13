package com.rlsp.ecommerce.conhecendoentitymanager;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPedido;
import org.junit.Assert;
import org.junit.Test;

public class FlushTest extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void chamarFlush() {
        try {
            entityManager.getTransaction().begin();

            Pedido pedido = entityManager.find(Pedido.class, 1);
            pedido.setStatus(StatusPedido.PAGO);

            /**
             * Forca o ENVIO/ATUALIZACAO fazendo a SINCRONIZACAO com o DB
             *  - mas nessa caso sera cancelado (pelo rollback())
             *  - Dificilmente o flush() ser utilizado
             *  - CASO REAL: Serve para liberar a memoria do EntityManager, para nao perderas operacoes ja feitas, para so entao usar o clear()
             */
            entityManager.flush();

            if (pedido.getPagamento() == null) {
                throw new RuntimeException("Pedido ainda não foi pago.");
            }

        /**
         *  -  Uma consulta obriga o JPA a sincronizar o que ele tem na memória (sem usar o flush explicitamente).
         *  - O flush sera feito ANTES da execucao d JPQL
         */        

//            Pedido pedidoPago = entityManager
//                    .createQuery("select p from Pedido p where p.id = 1", Pedido.class)
//                    .getSingleResult();
//            Assert.assertEquals(pedido.getStatus(), pedidoPago.getStatus());

            /**
             * O Commite possui um flush() embutido
             */
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
