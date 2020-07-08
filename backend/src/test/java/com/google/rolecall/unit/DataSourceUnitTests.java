package com.google.rolecall.unit;

import com.google.rolecall.config.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class DataSourceUnitTests {
  
  private Environment env;
  private DataSourceConfig config;

  private String dbName = "dbname";
  private String url = "url";
  private String username = "username";
  private String password = "password";
  private String connection = "connection";

  @BeforeEach
  public void init() {
    env = mock(Environment.class);
    config = spy(new DataSourceConfig(env));

    lenient().when(env.getProperty("spring.cloud.gcp.sql.databaseName")).thenReturn(dbName);
    lenient().when(env.getProperty("spring.datasource.url")).thenReturn(url);
    lenient().when(env.getProperty("spring.datasource.username")).thenReturn(username);
    lenient().when(env.getProperty("spring.datasource.password")).thenReturn(password);
    lenient().when(env.getProperty("spring.cloud.gcp.sql.instance-connection-name"))
        .thenReturn(connection);
    lenient().when(env.getProperty("spring.cloud.gcp.projectId")).thenReturn("");
    lenient().when(env.getProperty("cloud.secret.name")).thenReturn("");
  }

  @Test
  public void getLocalDataSource_success() throws Exception {
    // Execute
    DataSource result = config.getDataSourceLocalMySql();

    // Assert
    if (result instanceof HikariDataSource) {
      HikariDataSource src = (HikariDataSource) result;
      assert(src.getUsername()).equals(username);
      assert(src.getPassword()).equals(password);
      assert(src.getJdbcUrl()).equals(url);
      src.close();
      return;
    }
    fail();
  }
}
