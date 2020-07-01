package com.google.rolecall.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Initializes the entity manager factory for all transactions. 
 *  Initializes datasource in prod through configurations from application-prod.properties
 *  Does NOT initialize the DataSource which is setup via configurations in application-dev.properties in Dev.  
 */
@Configuration
@EnableJpaRepositories("com.google.rolecall.repos")
@EnableTransactionManagement
public class RepositoryConfig {

  @Autowired
  DataSource dataSource;

  @Autowired
  Environment env;

  @Profile("prod")
  @Bean
  public DataSource getDataSource() {
    String dbName = env.getProperty("spring.cloud.gcp.sql.databaseName");
    String userName = env.getProperty("spring.datasource.username");
    String password = env.getProperty("spring.datasource.password");
    String cloudSqlInstance = env.getProperty("spring.cloud.gcp.sql.instance-connection-name");;

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(String.format("jdbc:mysql:///%s", dbName));
    config.setUsername(userName);
    config.setPassword(password);

    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", cloudSqlInstance);

    dataSource = new HikariDataSource(config);

    return dataSource;
  }

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