package com.irunninglog.spring.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value = { "${env}" })
public class ContextConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ContextConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.irunninglog.api");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(jpaProperties());

        return em;
    }

    private Properties jpaProperties() {
        String autoDdl = environment.getRequiredProperty("jpaProperties.hibernate.hbm2ddl.auto");
        String dialect = environment.getRequiredProperty("jpaProperties.hibernate.dialect");
        String showSql = environment.getProperty("jpaProperties.hibernate.show_sql", Boolean.FALSE.toString());

        LOG.info("jpaProperties:hibernate.hbm2ddl.auto:{}", autoDdl);
        LOG.info("jpaProperties:hibernate.dialect:{}", dialect);
        LOG.info("jpaProperties:hibernate.show_sql:{}", showSql);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", autoDdl);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);

        return properties;
    }

    @Bean
    public DataSource dataSource(){
        String driver = environment.getRequiredProperty("dataSource.driverClassName");
        String url = environment.getRequiredProperty("dataSource.url");
        String username = environment.getRequiredProperty("dataSource.username");
        String password = environment.getRequiredProperty("dataSource.password");

        LOG.info("dataSource:{}:{}:{}:{}", driver, url, username, "***");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

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
