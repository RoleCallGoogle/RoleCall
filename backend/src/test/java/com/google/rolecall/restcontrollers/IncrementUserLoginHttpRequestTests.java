package com.google.rolecall.restcontrollers;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.google.rolecall.models.User;
import com.google.rolecall.repos.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IncrementUserLoginHttpRequestTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private UserRepository userRepo;

  private String requestForm = "/api/increment-login?userid=%d";

  private int[] userLogins;

  private void mockSave(int id, User user) {
    when(userRepo.findById(id)).thenReturn(Optional.of(user));
  }

  @BeforeEach
  public void init() {
    userLogins = new int[3];
    for(int i=1; i <=userLogins.length; i++) {
      userLogins[i-1] = i;

      User user = mock(User.class);

      when(userRepo.findById(i)).thenReturn(Optional.of(user));
      when(user.getId()).thenReturn(i);
      mockSave(i, user);
      when(user.getLoginCount()).thenAnswer(u -> {
        return userLogins[user.getId()-1];
      });
    }
  }
    
  @Test
  public void defaultGetUserLoginValue_success() throws Exception {
    // Setup
    String request1 = String.format(requestForm, 1);
    String request2 = String.format(requestForm, 3);

    // Execute
    String response1 = this.restTemplate.getForObject(request1, String.class);
    String response2 = this.restTemplate.getForObject(request2, String.class);
    System.out.println(response1);
    // Assert
    assert(response1).equals("1");
    assert(response2).equals("3");
  }
}
