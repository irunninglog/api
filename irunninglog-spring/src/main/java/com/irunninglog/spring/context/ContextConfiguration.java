package com.irunninglog.spring.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.irunninglog")
@EnableJpaRepositories(basePackages = {"com.irunninglog"})
public class ContextConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ContextConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.irunninglog");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(jpaProperties());

        return em;
    }

    private Properties jpaProperties() {
        JpaConfig config = new JpaConfig(environment.getRequiredProperty("jpa"));

        LOG.info("dataSource:{}", config);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", config.getAutoDdl());
        properties.setProperty("hibernate.dialect", config.getDialect());
        properties.setProperty("hibernate.show_sql", config.getShowSql());

        return properties;
    }

    @Bean
    public DataSource dataSource(){
        DataSourceConfig config = new DataSourceConfig(environment.getRequiredProperty("dataSource"));

        LOG.info("dataSource:{}", config);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

}
