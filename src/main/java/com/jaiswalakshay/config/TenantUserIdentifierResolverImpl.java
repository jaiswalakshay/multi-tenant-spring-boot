package com.jaiswalakshay.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantUserIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static String DEFAULT_TENANT_ID = "tenant_1";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = TenantContext.getTenantId();
        return (currentTenantId != null) ? currentTenantId : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}