package com.google.rolecall.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Initializes the entity manager factory for all transactions.
 *  Does NOT initialize the DataSource which is setup via configurations in application-dev.properties in Dev.  
 */
@Configuration
@EnableJpaRepositories("com.google.rolecall.repos")
@EnableTransactionManagement
public class RepositoryConfig {

  @Autowired
  DataSource dataSource;

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

    // TODO: Add shared caching here
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.google.rolecall.models");
    factory.setDataSource(dataSource);

    return factory;
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

    JpaTransactionManager txManager = new JpaTransactionManager();

    txManager.setEntityManagerFactory(entityManagerFactory);

    return txManager;
  }
}
