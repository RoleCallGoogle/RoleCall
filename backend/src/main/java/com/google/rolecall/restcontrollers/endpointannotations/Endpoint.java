package com.google.rolecall.restcontrollers.endpointannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Describes a generic REST enpoint class for mapping to any request types. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public @interface Endpoint {
  @AliasFor(annotation = RequestMapping.class, attribute = "path")
  String value();
}
