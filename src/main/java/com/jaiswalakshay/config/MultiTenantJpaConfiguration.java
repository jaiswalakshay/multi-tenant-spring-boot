package com.jaiswalakshay.config;

import com.jaiswalakshay.config.MultiTenantUserProperties.DataSourceProperties;
import com.jaiswalakshay.model.User;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({MultiTenantUserProperties.class, JpaProperties.class})
@EnableJpaRepositories(basePackages = {"com.jaiswalakshay.repository"}, transactionManagerRef = "txManager")
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private MultiTenantUserProperties multiTenantUserProperties;

    @Bean(name = "dataSourcesUser")
    public Map<String, DataSource> dataSourcesUser() {
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceProperties dsProperties : this.multiTenantUserProperties.getDataSources()) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create()
                    .url(dsProperties.getUrl())
                    .username(dsProperties.getUsername())
                    .password(dsProperties.getPassword())
                    .driverClassName(dsProperties.getDriverClassName());
            result.put(dsProperties.getTenantId(), factory.build());
        }
        return result;
    }

    @Bean
    @Scope("refresh")
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        // Autowires dataSourcesDvdRental
        return new UserDataSourceMultiTenantConnectionProviderImpl();
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new TenantUserIdentifierResolverImpl();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
                                                                           CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        // No dataSource is set to resulting entityManagerFactoryBean
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan(new String[]{User.class.getPackage().getName()});
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

//    @Bean(name = "flywayOne")
//    public Flyway flywayOne() {
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(userDataSourceMultiTenantConnectionProvider.selectDataSource("tenant_1"));
//        flyway.setLocations("db/migration");
//        return flyway;
//    }
//    /**
//     * Second flyway configuration use "flyway.locations" hard coded value.
//     */
//    @Bean(name = "flywaySecond")
//    public Flyway flywaySecond(DataSource dataSource) {
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(userDataSourceMultiTenantConnectionProvider.selectDataSource("tenant_2"));
//        flyway.setLocations("db/migration");
//        return flyway;
//    }
}