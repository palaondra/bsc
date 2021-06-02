package com.pala.delivery.run;

import com.pala.delivery.cache.BackfillCache;
import com.pala.delivery.common.CommonConstants;
import com.pala.delivery.data.Backfill;
import com.pala.delivery.data.BackfillFactory;
import com.pala.delivery.exception.InvalidInputArgumentException;
import com.pala.delivery.exception.InvalidPricesFileFormatException;
import com.pala.delivery.file_reader.InputBackfillFileReader;
import com.pala.delivery.file_reader.InputPricesFileReader;
import com.pala.delivery.validator.InputArgumentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class DeliveryApplicationRunner implements CommandLineRunner {

  private static final String QUIT_COMMAND = "quit";

  private static Logger LOG = LoggerFactory.getLogger(DeliveryApplicationRunner.class);

  @Autowired
  private InputArgumentValidator inputArgumentValidator;

  @Autowired
  private InputBackfillFileReader inputBackfillFileReader;

  @Autowired
  private InputPricesFileReader inputPricesFileReader;

  @Autowired
  private BackfillFactory backfillFactory;

  @Autowired
  private BackfillCache backfillCache;

  @Override
  public void run(String... args) throws Exception {

    try {
      // validate application's arguments, if some arguments exists
      inputArgumentValidator.validate(args);

      // read input prices file
      inputPricesFileReader.readFile(args);

      // read input backfill file
      inputBackfillFileReader.readFile(args);

    } catch (InvalidInputArgumentException invalidInputArgumentException) {
      LOG.error(
        "Invalid input argument, only {} or {} argument is allowed",
        CommonConstants.BACKFILL_FILE_INPUT_ARGUMENT,
        CommonConstants.PRICES_FILE_INPUT_ARGUMENT,
        invalidInputArgumentException.getMessage());
      return;

    } catch (FileNotFoundException fileNotFoundException) {
      return;
    } catch (InvalidPricesFileFormatException invalidPricesFileFormatException) {
      return;
    }

    // read user input
    Scanner scanner = new Scanner(System.in);

    String line = "";
    do {
      System.out.println("Please input .....");
      line = scanner.nextLine();

      Backfill backfill = backfillFactory.getBackfill(line);
      if (!ObjectUtils.isEmpty(backfill)) {
        backfillCache.addBackfill(backfill);
      }

    } while (!line.equalsIgnoreCase(QUIT_COMMAND));
  }
}
