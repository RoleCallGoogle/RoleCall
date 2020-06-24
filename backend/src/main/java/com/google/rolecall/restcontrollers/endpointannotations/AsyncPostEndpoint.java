package com.google.rolecall.restcontrollers.endpointannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** POST request methods of an @Endpoint class. */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Async
@RequestMapping(method = RequestMethod.POST)
public @interface AsyncPostEndpoint {}
