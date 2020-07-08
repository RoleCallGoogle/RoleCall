package com.google.rolecall;

import com.google.rolecall.models.User;
import com.google.rolecall.repos.UserRepository;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** Methods to be run before and after the server loads. */
@Component
public class ApplicationLoader implements ApplicationRunner {

  private Logger logger = Logger.getLogger(ApplicationLoader.class.getName());
  
  private final Environment environment;
  private final UserRepository userRepo;
  private String adminFirstName;
  private String adminLastName;
  private String adminEmail;

  @Profile({"dev","prod"})
  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Initialize admin if neccessary
    adminFirstName = environment.getProperty("admin.first.name");
    adminLastName = environment.getProperty("admin.last.name");
    adminEmail = environment.getProperty("admin.email");
    Optional<User> possibleAdmin = userRepo.findByFirstNameAndLastNameAndEmailIgnoreCase(
        adminFirstName, adminLastName, adminEmail);
    possibleAdmin.ifPresentOrElse(this::adminExists, this::createAdmin);
  }

  private void adminExists(User user) {
    logger.log(Level.INFO, String.format("Admin User already exists: %s %s %s", 
        user.getFirstName(), user.getLastName(), user.getEmail()));
  }

  private void createAdmin() {
    userRepo.save(new User(adminFirstName, adminLastName, adminEmail));
    logger.log(Level.WARNING, String.format("Admin User Created: %s %s %s", 
        adminFirstName, adminLastName, adminEmail));
  }

  @Autowired
  public ApplicationLoader(Environment env, UserRepository userRepo) {
    this.environment = env;
    this.userRepo = userRepo;
  }
}
