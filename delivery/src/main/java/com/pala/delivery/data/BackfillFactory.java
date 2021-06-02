package com.pala.delivery.data;

import com.pala.delivery.exception.InvalidBackfillEntityException;
import com.pala.delivery.exception.WrongUserInputException;
import com.pala.delivery.run.DeliveryApplication;
import com.pala.delivery.validator.BackfillValidator;
import com.pala.delivery.validator.InputLineValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackfillFactory {

  private static Logger LOG = LoggerFactory.getLogger(DeliveryApplication.class);

  @Autowired
  private BackfillValidator backfillValidator;

  @Autowired
  private InputLineValidator inputLineValidator;

  public Backfill getBackfill(String inputLine) {

    try {
      // validate inputLine
      inputLineValidator.validate(inputLine);

      // inputLine is valid => we will split it
      // split inputLine string
      String[] splitter = inputLine.split("\\s+");

      // create Backfill instance
      Backfill backfill = new Backfill(Double.parseDouble(splitter[0]), splitter[1]);

      // validate Backfill instance
      backfillValidator.validate(backfill);

      return backfill;

    } catch (WrongUserInputException wrongUserInputException) {
      LOG.warn("Wrong user input, example or correct input: 3.444 67904");

    } catch (NumberFormatException numberFormatException) {
      LOG.warn("Wrong weight value, weight can be only number");

    } catch (InvalidBackfillEntityException invalidBackfillEntityException) {
      LOG.warn("Wrong Backfill entity, please check your input, input example: 3.444, 67904");
    }

    return null;
  }

}
