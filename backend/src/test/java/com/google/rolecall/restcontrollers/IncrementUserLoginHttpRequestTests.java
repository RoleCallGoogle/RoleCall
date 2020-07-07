package com.google.rolecall.restcontrollers;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IncrementUserLoginHttpRequestTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private UserRepository userRepo;

  private String requestForm = "/api/increment-login?userid=%d";

  @BeforeEach
  public void init() {
    for(int i=1; i <=3; i++) {
      User user = new User();
      for(int c =0; c<i; c++) {
        user.incrementLogin();
      }
      when(userRepo.findById(i)).thenReturn(Optional.of(user));
      when(userRepo.save(user)).thenReturn(user);
    }
  }
    
  @Test
  public void getUserLoginValue_success() throws Exception {
    // Setup
    String request1 = String.format(requestForm, 1);
    String request2 = String.format(requestForm, 3);

    // Execute
    String response1 = this.restTemplate.getForObject(request1, String.class);
    String response2 = this.restTemplate.getForObject(request2, String.class);
    
    // Assert
    assert(response1).equals("1");
    assert(response2).equals("3");
  }

  @Test
  public void invalidUserLoginValue_failure() throws Exception {
    // Setup
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
    String request = String.format(requestForm, 4);

    // Execute
    ResponseEntity<String> response = this.restTemplate.exchange(request,
        HttpMethod.GET, entity, String.class);
    
    // Assert
    assert(response.getStatusCode().isError());
  }

  @Test
  public void incrementUserLoginValue_success() throws Exception {
    // Setup
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
    String request = String.format(requestForm, 1);

    // Execute
    ResponseEntity<String> response = this.restTemplate.exchange(request,
        HttpMethod.POST, entity, String.class);
        
    // Assert
    assert(response.getBody()).equals("2");
  }
}
