package com.rlsp.ecommerce.listener;

import javax.persistence.PrePersist;

import javax.persistence.PreUpdate;

import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.service.NotaFiscalService;

/**
 * Quando o PEDIDO for PAGO, sera GERADA um NOTA FISCAL
 */

/**
 * LISTENERS *
 */
public class GerarNotaFiscalListener {

    private NotaFiscalService notaFiscalService = new NotaFiscalService();

    @PrePersist
    @PreUpdate
    public void gerar(Pedido pedido) {
        if (pedido.isPago() && pedido.getNotaFiscal() == null) {
            notaFiscalService.gerar(pedido);
        }
    }
}
