package com.google.rolecall.restcontrollers;

import java.util.concurrent.CompletableFuture;

import com.google.rolecall.restcontrollers.endpointannotations.Annotations.AsyncGetEndpoint;
import com.google.rolecall.restcontrollers.endpointannotations.Annotations.AsyncPostEndpoint;
import com.google.rolecall.restcontrollers.endpointannotations.Annotations.Endpoint;

import org.springframework.web.bind.annotation.RequestParam;

/** Initial REST Controller for the RoleCall Proof of Concept (POC). */
@Endpoint("/api/test")
public class TestController extends AsyncRestEndpoint {
  
	@AsyncGetEndpoint
	public CompletableFuture<String> sayHello(@RequestParam(value="value", required=false, defaultValue="default") String value) {
	  return CompletableFuture.completedFuture("Hello " + value);
  }
  
  @AsyncPostEndpoint
	public CompletableFuture<String> throwException() throws Exception {
	  throw new Exception("Purposeful exception");
	}
}
