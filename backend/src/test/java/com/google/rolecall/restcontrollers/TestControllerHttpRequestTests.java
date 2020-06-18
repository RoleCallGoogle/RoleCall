package com.google.rolecall.restcontrollers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/** Unit tests for the /api/test enpoint for the backend server. */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestControllerHttpRequestTests {
    
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
    
  @Test
  public void defaultValueReturn_success() throws Exception {
    assert(this.restTemplate.getForObject("http://localhost:" + port + "/api/test",
      String.class)).equals("Hello default");
  }

  @Test
  public void paramValueReturn_success() throws Exception {
    String value = "val";
    String request = "http://localhost:" + port + "/api/test?value=" + value;
    assert(this.restTemplate.getForObject(request, String.class)).equals("Hello " + value);
  }
}
