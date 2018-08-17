package com.jaiswalakshay.config;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

public class UserDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final long serialVersionUID = 1L;

    @Autowired
    private Map<String, DataSource> dataSourcesUser;

    @Override
    public DataSource selectAnyDataSource() {
        return this.dataSourcesUser.values().iterator().next();
    }

    @Override
    public DataSource selectDataSource(String tenantIdentifier) {
        return this.dataSourcesUser.get(tenantIdentifier);
    }

    public Set<String> getAllTenants(){
        return this.dataSourcesUser.keySet();
    }
}