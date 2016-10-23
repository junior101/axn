package com.axn.database.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Test configuration class for Spring Data, Hibernate and JPA
 * Using embedded database H2
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.axn.database.repository" })
@ComponentScan(basePackages = "com.axn.database")
public class ConfigTestDataSpring {
    /**
     * Setup test datasource for embedded database (H2)
     *
     * @return DataSource
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        final EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("h2_test_drop_create_tables.sql")
                .build();
    }

    /**
     * Setup test vendor adapter for jpa
     *
     * @return JpaVendorAdapter
     */
    @Bean(name = "vendorAdapter")
    public JpaVendorAdapter getVendorAdapter() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);
        return vendorAdapter;
    }

    /**
     * Setup test factory manager for jpa
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(getVendorAdapter());
        entityManagerFactory.setPackagesToScan("com.axn.database.entity");
        entityManagerFactory.setDataSource(getDataSource());
        return entityManagerFactory;
    }

    /**
     * Setup test transaction manager for jpa
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager getTransactionManager(){
        return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
    }
}
