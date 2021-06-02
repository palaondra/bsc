package com.pala.delivery.validator;

import com.pala.delivery.exception.InvalidInputArgumentException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.pala.delivery.common.CommonConstants.BACKFILL_FILE_INPUT_ARGUMENT;
import static com.pala.delivery.common.CommonConstants.PRICES_FILE_INPUT_ARGUMENT;

/**
 * Validate allowed input arguments.
 */
@Component
public class InputArgumentValidator {

  private static final String TOO_MUCH_ARGUMENTS_ERROR = "TooMuchArguments";
  private static final String FILENAME_ARGUMENT_FILE_PATH_DOESNT_EXISTS_ERROR = "FilePathDoesntExists";

  public void validate(String... args) throws InvalidInputArgumentException {
    // some argument passed
    if (args.length > 0) {
      validateInputArgument(args, BACKFILL_FILE_INPUT_ARGUMENT);
      validateInputArgument(args, PRICES_FILE_INPUT_ARGUMENT);
    }
  }

  private void validateInputArgument(String[] args, String inputArgument) throws InvalidInputArgumentException {
    long numberOfInputArguments =
      Arrays.stream(args)
        .filter(p -> p.equals(inputArgument))
        .count();

    if (numberOfInputArguments > 1) {
      // invalid number of input arguments arguments
      throw new InvalidInputArgumentException(generateErrorCode(TOO_MUCH_ARGUMENTS_ERROR, inputArgument));
    }

    if (numberOfInputArguments == 1) {
      // try to find if after input argument is some file name
      // find argument's index
      int inputArgumentIndex = IntStream.range(0, args.length)
        .filter(arg -> inputArgument.equals(args[arg]))
        .findFirst()
        .orElse(-1);

      try {
        String filePath = args[inputArgumentIndex + 1];
      } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        throw new InvalidInputArgumentException(FILENAME_ARGUMENT_FILE_PATH_DOESNT_EXISTS_ERROR);
      }
    }

  }

  private String generateErrorCode(String errorCode, String inputArgument) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(errorCode);
    stringBuffer.append(": ");
    stringBuffer.append(inputArgument);

    return stringBuffer.toString();
  }

}
