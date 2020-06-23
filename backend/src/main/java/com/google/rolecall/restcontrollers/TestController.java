package com.google.rolecall.restcontrollers;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Initial REST Controller for the RoleCall Proof of Concept (POC). */
@RestController
public class TestController extends AsyncRestEndpoint {
  
  @Async
	@GetMapping("/api/test")
	public CompletableFuture<String> sayHello(@RequestParam(value="value", required=false, defaultValue="default") String value) {
	  return CompletableFuture.completedFuture("Hello " + value);
	}
}
