package com.google.rolecall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final Environment env;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/api/**").allowedOrigins(env.getProperty("rolecall.frontend.url"))
          .allowedMethods("GET", "POST", "PATCH", "DELETE");
  }

  public WebConfig(Environment env) {
    this.env = env;
  }
}