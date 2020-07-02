package com.google.rolecall.config;

import javax.sql.DataSource;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.Secret;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfig {

  @Autowired
  Environment env;

  @Profile("dev")
  @Bean
  public DataSource getDataSourceLocalMySql() {

    String url = env.getProperty("spring.datasource.url");
    String userName = env.getProperty("spring.datasource.username");
    String password = env.getProperty("spring.datasource.password");

    System.out.println("URL: "+url+" UName: "+userName+ " Pass: "+password);

    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

    dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
    dataSourceBuilder.url(url);
    dataSourceBuilder.username(userName);
    dataSourceBuilder.password(password);

    return dataSourceBuilder.build();
  }

  @Profile("prod")
  @Bean
  public DataSource getDataSourceCloudSql() {
    String dbName = env.getProperty("spring.cloud.gcp.sql.databaseName");
    String userName = env.getProperty("spring.datasource.username");
    String password = getCloudDBPassword();
    String cloudSqlInstance = env.getProperty("spring.cloud.gcp.sql.instance-connection-name");

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(String.format("jdbc:mysql:///%s", "rolecall-cloudsql"));
    config.setUsername(userName);
    config.setPassword(password);

    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", cloudSqlInstance);

    return new HikariDataSource(config);
  }

  private String getCloudDBPassword() {
    String password;

    try {
      SecretManagerServiceClient client = SecretManagerServiceClient.create();
      SecretVersionName name = SecretVersionName.of("project-role-call-dev", "rolecall_user_password", "1");

      AccessSecretVersionResponse response = client.accessSecretVersion(name);

      password = response.getPayload().getData().toStringUtf8();

    } catch (Exception e) {
      throw new Error(e);
    }

    return password;
  }
}
