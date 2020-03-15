package com.rlsp.ecommerce.mapeamentoavancado;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.rlsp.ecommerce.EntityManagerTest;
import com.rlsp.ecommerce.model.NotaFiscal;
import com.rlsp.ecommerce.model.Pedido;
import com.rlsp.ecommerce.model.Produto;

public class SalvandoArquivosTest extends EntityManagerTest {

    @Test
    public void salvarXmlNota() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(carregarNotaFiscal());

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();
        
        entityManager.clear();

        NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        Assert.assertNotNull(notaFiscalVerificacao.getXml());
        Assert.assertTrue(notaFiscalVerificacao.getXml().length > 0);

        /**
         * Salva um  arquivo na pasta do usuario
         */
        
        /**
        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(
                            System.getProperty("user.home") + "/xml.xml")).toFile());
            out.write(notaFiscalVerificacao.getXml());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        */
        
        
    }
    
    @Test
    public void salvarFotoProduto() {
        
        Produto produto = entityManager.find(Produto.class, 1);
        
        entityManager.getTransaction().begin();
        produto.setFotoProduto(carregarFoto());
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, 1);
        Assert.assertNotNull(produtoVerificacao.getFotoProduto());
        Assert.assertTrue(produtoVerificacao.getFotoProduto().length > 0);
    }
    
    public static byte[] carregarFoto() {
        return carregarArquivo("/kindle.jpg");
    }

    public static byte[] carregarNotaFiscal() {
        return carregarArquivo("/nota-fiscal.xml");
    }

    private static byte[] carregarArquivo(String nome) {
        try {
            return SalvandoArquivosTest.class.getResourceAsStream(nome).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] carregarNotaFiscalXml() {
        try {
            return SalvandoArquivosTest.class.getResourceAsStream(
                    "/nota-fiscal.xml").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
