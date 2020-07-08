package com.google.rolecall.unit;

import com.google.rolecall.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserUnitTests {

  private User user;
  private String firstName = "Jared";
  private String lastName = "Hirsch";
  private String email = "email@email.com";

  @BeforeEach
  public void init() {
    user = new User(firstName, lastName, email);
  }

  @Test
  public void setFirstName_success() throws Exception {
    //Execute
    user.setFirstName("NotJared");

    // Assert
    assert(user.getFirstName()).equals("NotJared");
  }

  @Test
  public void setLastName_success() throws Exception {
    //Execute
    user.setLastName("NotHirsch");

    // Assert
    assert(user.getLastName()).equals("NotHirsch");
  }

  @Test
  public void setEmail_success() throws Exception {
    //Execute
    user.setEmail("Notemail@email.com");

    // Assert
    assert(user.getEmail()).equals("Notemail@email.com");
  }

  /** A User object created but not saved to the database should not have an id. */
  @Test
  public void getId_failure() throws Exception {
    // Assert
    assert(user.getId() == null);
  }
}