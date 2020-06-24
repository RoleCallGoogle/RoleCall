package com.google.rolecall.restcontrollers;

import java.util.concurrent.CompletableFuture;

import com.google.rolecall.restcontrollers.endpointannotations.AsyncGetEndpoint;
import com.google.rolecall.restcontrollers.endpointannotations.Endpoint;

import org.springframework.web.bind.annotation.RequestParam;

/** Initial REST Controller for the RoleCall Proof of Concept (POC). */
@Endpoint("/api/test")
public class TestController extends AsyncRestEndpoint {
  
	@AsyncGetEndpoint
	public CompletableFuture<String> sayHello(@RequestParam(value="value", required=false, defaultValue="default") String value) {
	  return CompletableFuture.completedFuture("Hello " + value);
	}
}
