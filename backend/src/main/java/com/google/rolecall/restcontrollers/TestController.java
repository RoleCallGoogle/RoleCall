package com.google.rolecall.restcontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {
    
	@RequestMapping("/")
	public String index() {
	  return "Welcome to spring";
	}
  
	@RequestMapping("/test")
	public String sayHello(@RequestParam(value="value", required=false, defaultValue="1") String value) {
	  return "Test " + value;
	}
}