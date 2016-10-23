package com.axn.database.spring;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class for Spring Data, Hibernate and JPA
 */
@Configuration
@PropertySource("classpath:env.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.axn.database.repository" })
@ComponentScan(basePackages = "com.axn.database")
public class ConfigDataSpring {
    private final Environment environment;

    @Autowired
    public ConfigDataSpring(Environment environment) {
        this.environment = environment;
    }

    /**
     * Get type of database by property
     * Support: postgresql
     *
     * @return Database
     */
    private Database getDatabase() {
        switch(environment.getProperty("database.type")) {
            case "postgresql":
                return Database.POSTGRESQL;
            case "h2":
                return Database.H2;
            default:
                return Database.POSTGRESQL;
        }
    }

    /**
     * Get properties bundle for jpa and setup hibernate properties
     *
     * @return Properties
     */
    private Properties getJpaProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", environment.getProperty("database.dialect"));
                setProperty("hibernate.format_sql", environment.getProperty("database.logging.format_sql"));
                setProperty("hibernate.use_sql_comments", environment.getProperty("database.logging.sql_comments"));
                setProperty("hibernate.show_sql", environment.getProperty("database.logging.show_sql"));
            }
        };
    }

    /**
     * Setup datasource with HikariCP connection pool
     *
     * @return DataSource
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(Integer.parseInt(environment.getProperty("database.maximum_pool_size")));
        dataSource.setDriverClassName(environment.getProperty("database.driver"));
        dataSource.setJdbcUrl(environment.getProperty("database.url"));
        dataSource.addDataSourceProperty("user", environment.getProperty("database.user"));
        dataSource.addDataSourceProperty("password", environment.getProperty("database.password"));
        dataSource.addDataSourceProperty("cachePrepStmts", environment.getProperty("database.prepared_statements.cache"));
        dataSource.addDataSourceProperty("prepStmtCacheSize", environment.getProperty("database.prepared_statements.cache_size"));
        dataSource.addDataSourceProperty("useServerPrepStmts", environment.getProperty("database.prepared_statements.use"));
        return dataSource;
    }

    /**
     * Setup vendor adapter for jpa
     *
     * @return JpaVendorAdapter
     */
    @Bean(name = "vendorAdapter")
    public JpaVendorAdapter getVendorAdapter() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(getDatabase());
        vendorAdapter.setShowSql(environment.getProperty("database.logging.show_sql").equals("true"));
        return vendorAdapter;
    }

    /**
     * Setup factory manager for jpa
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("com.axn.database.entity");
        entityManagerFactory.setJpaVendorAdapter(getVendorAdapter());
        entityManagerFactory.setJpaProperties(getJpaProperties());
        entityManagerFactory.setDataSource(getDataSource());
        return entityManagerFactory;
    }

    /**
     * Setup transaction manager for jpa
     *
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager getTransactionManager(){
        return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
    }
}
