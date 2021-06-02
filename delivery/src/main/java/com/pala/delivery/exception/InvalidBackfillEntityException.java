package com.pala.delivery.exception;

public class InvalidBackfillEntityException extends RuntimeException {

  public InvalidBackfillEntityException(String errorMessage) {
    super(errorMessage);
  }

}
