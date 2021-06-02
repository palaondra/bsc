package com.pala.delivery.validator;

import com.pala.delivery.exception.WrongUserInputException;
import org.springframework.stereotype.Component;

/**
 * Class for basic validation of inputted line by user.
 */
@Component
public class InputLineValidator {

  private static final String INPUT_LINE_NULL_OR_EMPTY = "inputLineNullOrEmpty";
  private static final String INPUT_LINE_WRONG_FORMAT = "inputLineWrongFormat";

  public void validate(String inputLine) throws WrongUserInputException {

    if (inputLine == null || (inputLine != null && inputLine.length() == 0)) {
      throw new WrongUserInputException(INPUT_LINE_NULL_OR_EMPTY);

    } else {
      // try to split line by space
      String[] splitter = inputLine.split("\\s+");

      if (splitter.length != 2) {
        throw new WrongUserInputException(INPUT_LINE_WRONG_FORMAT);
      }
    }
  }
}
