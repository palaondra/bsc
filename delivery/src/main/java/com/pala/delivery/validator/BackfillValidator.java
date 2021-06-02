package com.pala.delivery.validator;

import com.pala.delivery.exception.InvalidBackfillEntityException;
import org.springframework.stereotype.Component;
import com.pala.delivery.data.Backfill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for <code>Backfill</code> entity validations.
 */
@Component
public class BackfillValidator {

  private static final String POST_CODE_REGEXP = "\\d{5}";
  private static final String WEIGHT_NEGATIVE_VALUE_ERROR = "WeightHasNegativeValue";
  private static final String DESTINATION_INVALID_POST_CODE_ERROR = "DestinationHasInvalidPostCode";
  private static final String DESTINATION_IS_NULL_OR_EMPTY = "DestinationIsNullOrEmpty";
  private static final String WEIGHT_IS_NULL = "WeightIsNull";
  private static final String WEIGHT_WRONG_NUMBER_OF_DECIMAL_PLACES_ERROR = "WeightHasWrongNumberOfDecimalPlaces";
  private static final String WEIGHT_WRONG_DECIMAL_PLACE_CHARACTER = "WeightHasWrongDecimalPlaceCharacter";

  /**
   * Validate input <code>Backfill</code> entity.
   *
   * @param backfill Input <code>Backfill</code> class instance.
   */
  public void validate(Backfill backfill) throws InvalidBackfillEntityException {
    if (backfill.getDestination() == null || (backfill.getDestination() != null && backfill.getDestination().length() == 0)) {
      throw new InvalidBackfillEntityException(DESTINATION_IS_NULL_OR_EMPTY);
    }

    if (backfill.getWeight() == null) {
      throw new InvalidBackfillEntityException(WEIGHT_IS_NULL);
    }

    validateWeight(backfill.getWeight());
    validateDestination(backfill.getDestination());
  }

  private void validateDestination(String destination) throws InvalidBackfillEntityException {
    Pattern pattern = Pattern.compile(POST_CODE_REGEXP);
    Matcher matcher = pattern.matcher(destination);

    if (!matcher.find()) {
      throw new InvalidBackfillEntityException(DESTINATION_INVALID_POST_CODE_ERROR);
    }
  }

  private void validateWeight(Number weight) throws InvalidBackfillEntityException {
    Double weightDouble = weight.doubleValue();

    if (weightDouble < 0) {
      throw new InvalidBackfillEntityException(WEIGHT_NEGATIVE_VALUE_ERROR);
    } else {
      String[] splitter = weightDouble.toString().split("\\.");

      if (splitter.length == 0) {
        throw new InvalidBackfillEntityException(WEIGHT_WRONG_DECIMAL_PLACE_CHARACTER);
      } else {
        // before decimal place
        splitter[0].length();

        // after decimal place
        int decimalLength = splitter[1].length();

        if (decimalLength > 3) {
          throw new InvalidBackfillEntityException(WEIGHT_WRONG_NUMBER_OF_DECIMAL_PLACES_ERROR);
        }
      }

    }
  }
}
