package com.pala.delivery.exception;

public class WrongUserInputException extends RuntimeException{

  public WrongUserInputException(String errorMessage) {
    super(errorMessage);
  }

}
