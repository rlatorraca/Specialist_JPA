package com.rlsp.ecommerce.jpql;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.NotaFiscal;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.StatusPagamento;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PassandoParametrosTest extends EntityManagerTest {

    @Test
    public void passarParametroDate() {
        String jpql = "select nf from NotaFiscal nf where nf.dataEmissao <= ?1";
        

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP); //Usado para java.util.Date E java.util.Calander

        List<NotaFiscal> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 1);
    }

    @Test
    public void passarParametro() {
        String jpql = "select p from Pedido p join p.pagamento pag " +
                " where p.id = :pedidoId and pag.status = ?1";
        
        //String jpql = "select p from Pedido p join p.pagamento pag where p.id = :?1 and pag.status = ?2";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        //typedQuery.setParameter(1, 2);
        typedQuery.setParameter("pedidoId", 2);
        typedQuery.setParameter(1, StatusPagamento.RECEBIDO);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 1);
    }
}