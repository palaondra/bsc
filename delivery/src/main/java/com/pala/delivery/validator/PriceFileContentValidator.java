package com.pala.delivery.validator;

import com.pala.delivery.exception.InvalidPricesFileFormatException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pala.delivery.common.CommonConstants.SPACE_REGEXP;

@Component
public class PriceFileContentValidator {

  private static final String INVALID_PRICE_FILE_CONTENT_FORMAT_ERROR = "InvalidPriceFileContentFormat";

  public void validate(List<String> priceFileLinesList) throws InvalidPricesFileFormatException {
    // check if input prices file has correct format:
    // from highest values to lower values
    for (int i = 0; i < priceFileLinesList.size(); i++) {
      String[] splitedLine = priceFileLinesList.get(i).split(SPACE_REGEXP);

      int nextIndex = i + 1;
      if (nextIndex < priceFileLinesList.size()) {
        String[] splitedLineOnNextIndex = priceFileLinesList.get(nextIndex).split(SPACE_REGEXP);

        if (!(Double.parseDouble(splitedLine[0]) > Double.parseDouble(splitedLineOnNextIndex[0])) ||
          !(Double.parseDouble(splitedLine[1]) > Double.parseDouble(splitedLineOnNextIndex[1]))) {
          throw new InvalidPricesFileFormatException(INVALID_PRICE_FILE_CONTENT_FORMAT_ERROR);
        }
      }
    }
  }

}
