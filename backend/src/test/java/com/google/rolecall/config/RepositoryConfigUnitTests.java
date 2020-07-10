package com.google.rolecall.config;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretPayload;
import static com.google.common.truth.Truth.assertThat;
import com.google.protobuf.ByteString;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.IOException;

import org.springframework.mock.env.MockEnvironment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class RepositoryConfigUnitTests {
  
  private DataSource dataSource;
  private RepositoryConfig config;

  @BeforeEach
  public void init() {
    dataSource = mock(DataSource.class);
    config = new RepositoryConfig(dataSource);
  }

  @Test
  public void getEntityManagerFactory_success() throws Exception {
    // Execute
    LocalContainerEntityManagerFactoryBean factory = config.entityManagerFactory();

    // Assert
    assert(factory.getDataSource()).equals(dataSource);
    assertThat(factory.getJpaVendorAdapter()).isInstanceOf(HibernateJpaVendorAdapter.class);
  }

  @Test
  public void getTransactionManager_success() throws Exception {
    // Setup
    EntityManagerFactory factory = mock(EntityManagerFactory.class);

    // Execute
    PlatformTransactionManager txnManager = config.transactionManager(factory);
    
    // Assert
    assertThat(txnManager).isInstanceOf(JpaTransactionManager.class);
    assert(((JpaTransactionManager) txnManager).getEntityManagerFactory()).equals(factory);
  }
}
