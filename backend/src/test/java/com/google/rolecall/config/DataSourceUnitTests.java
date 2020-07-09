package com.google.rolecall.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretPayload;
import static com.google.common.truth.Truth.assertThat;
import com.google.protobuf.ByteString;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class DataSourceUnitTests {

  private DataSourceConfig config;

  private String dbName = "dbname";
  private String url = "url";
  private String username = "username";
  private String password = "password";
  private String connection = "connection";
  private String projectId = "proj";
  private String secretName = "secret";

  @BeforeEach
  public void init() {
    MockEnvironment enviroment = new MockEnvironment();
    config = spy(new DataSourceConfig(enviroment));

    enviroment.setProperty("spring.cloud.gcp.sql.databaseName", dbName);
    enviroment.setProperty("spring.datasource.url", url);
    enviroment.setProperty("spring.datasource.username", username);
    enviroment.setProperty("spring.datasource.password", password);
    enviroment.setProperty("spring.cloud.gcp.sql.instance-connection-name", connection);
    enviroment.setProperty("spring.cloud.gcp.projectId", projectId);
    enviroment.setProperty("cloud.secret.name", secretName);
  }

  @Test
  public void getLocalDataSource_success() throws Exception {
    // Execute
    DataSource result = config.getDataSourceLocalMySql();

    // Assert
    assertThat(result).isInstanceOf(HikariDataSource.class);
    HikariDataSource src = (HikariDataSource) result;
    assert(src.getUsername()).equals(username);
    assert(src.getPassword()).equals(password);
    assert(src.getJdbcUrl()).equals(url);
    src.close();
  }

  @Test
  public void getCloudDataSource_success() throws Exception {
    // Mock
    try {
      doReturn(AccessSecretVersionResponse.newBuilder()
          .setPayload(SecretPayload.newBuilder()
          .setData(ByteString.copyFrom(password.getBytes())))
          .build())
          .when(config).getSecretResponse(eq(projectId), eq(secretName));
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Execute
    HikariConfig result = config.getCloudConfig();

    // Assert
    assert(result.getUsername()).equals(username);
    assert(result.getPassword()).equals(password);
    assert(result.getJdbcUrl()).equals(String.format("jdbc:mysql:///%s", dbName));
  }
}
