package com.google.rolecall.restcontrollers;

/** Custom exceptions for handling invalid request based errors */
public final class RequestExceptions {
  
  public static class InvalidArgumentException extends Exception{

    public InvalidArgumentException(String message) {
      super(message);
    }

    private static final long serialVersionUID = 398486992691471219L;
  } 

  public static class UnsupportedOperationException extends Exception{

    public UnsupportedOperationException(String message) {
      super(message);
    }

    private static final long serialVersionUID = 1892786199356723273L;
  }

  public static class InvalidPermissionsException extends Exception{

    public InvalidPermissionsException(String message) {
      super(message);
    }

    private static final long serialVersionUID = 9098340870678943547L;
  }
}
