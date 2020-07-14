package com.google.rolecall.restcontrollers.exceptionhandling;

import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.EntityNotFoundException;
import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.UnimplementedOperationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/* Handles controller level default exception handling and custom exception exception handling. */
@RestControllerAdvice
public class CustomControllerExceptionHandler extends ResponseEntityExceptionHandler {

  // Override specific default exception responses.
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String invalidURL = ex.getRequestURL();
    ErrorResponse error = new ErrorResponse(String.format("Path %s does not exist.", invalidURL),
        status.value());

    return error.getResponse(headers);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, 
      HttpStatus status, WebRequest request) {
    String message = String.format("Parameter %s was not of type %s.", ex.getParameterName(),
        ex.getParameterType());
    ErrorResponse error = new ErrorResponse(message, status.value());

    return error.getResponse(headers);
  }

  // Override general exception handler response format.
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), status.value());

    return error.getResponse(headers);
  }

  // Override Custom Exceptions
  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ErrorResponse handleEntityNotFound(EntityNotFoundException ex) {
    return new ErrorResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
  }

  @ExceptionHandler(UnimplementedOperationException.class)
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  protected ErrorResponse handleUnimplementedOperation(UnimplementedOperationException ex) {
    return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED.value());
  }
}
