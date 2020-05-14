package com.rlsp.ecommerce.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * Ecm = Ecommerce (apenas uma nomeclatura) 
 *
 */
public class EcmCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	/**
	 * Serve para ser usado  na WEB
	 *  - Configura os Tenants por REQUISICAO
	 */
    private static ThreadLocal<String> tl = new ThreadLocal<>();

    /**
     * Passa o NOME DO SCHEMA que sera usado pelo TENANT (identifica o SCHEMA)      
     */
    public static void setTenantIdentifier(String tenantId) {
        tl.set(tenantId);
    }

    /**
     * Para RECUPERA qual TENANT esta sendo usado
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        return tl.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
