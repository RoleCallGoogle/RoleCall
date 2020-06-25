package com.google.rolecall.restcontrollers;

import java.util.concurrent.CompletableFuture;

import com.google.rolecall.restcontrollers.Annotations.Get;
import com.google.rolecall.restcontrollers.Annotations.Post;
import com.google.rolecall.restcontrollers.Annotations.Endpoint;

import org.springframework.web.bind.annotation.RequestParam;

/** Initial REST Controller for the RoleCall Proof of Concept (POC). */
@Endpoint("/api/test")
public class TestController extends AsyncRestEndpoint {
  
  @Get
  public CompletableFuture<String> sayHello(@RequestParam(value="value", required=false, defaultValue="default") String value) {
    return CompletableFuture.completedFuture(String.format("Hello %s", value));
  }

  @Post
  public CompletableFuture<String> throwException() throws Exception {
    throw new UnsupportedOperationException("POST is not defined for this endpoint");
  }
}
