package com.google.rolecall;

import java.net.InetAddress;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.rolecall.models.User;
import com.google.rolecall.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** Methods to be run before and after the server loads. */
@Component
public class ApplicationLoader implements ApplicationRunner {

  @Autowired
  private Environment environment;

  @Autowired
  private UserRepository userRepo;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("Hostname: " + environment.getProperty("java.rmi.server.hostname"));
    System.out.println("Port: " + environment.getProperty("server.port"));
    System.out.println("Address: " + InetAddress.getLocalHost().getHostAddress());
  }
}
