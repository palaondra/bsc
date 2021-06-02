package com.pala.delivery.file_reader;

import com.pala.delivery.cache.PricesCache;
import com.pala.delivery.data.BackfillPrice;
import com.pala.delivery.exception.InvalidPricesFileFormatException;
import com.pala.delivery.validator.PriceFileContentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static com.pala.delivery.common.CommonConstants.PRICES_FILE_INPUT_ARGUMENT;
import static com.pala.delivery.common.CommonConstants.SPACE_REGEXP;

/**
 * Read input file with backfills. This file has this structure:
 * packageWeight fee
 */
@Component
public class InputPricesFileReader {

  private static Logger LOG = LoggerFactory.getLogger(InputPricesFileReader.class);

  @Autowired
  private PricesCache pricesCache;

  @Autowired
  private PriceFileContentValidator priceFileContentValidator;

  public void readFile(String... args) throws FileNotFoundException, InvalidPricesFileFormatException {
    String filePath = "";

    // find -pricesFile argument's index
    int pricesFileArgIndex = IntStream.range(0, args.length)
      .filter(arg -> PRICES_FILE_INPUT_ARGUMENT.equals(args[arg]))
      .findFirst()
      .orElse(-1);

    if (pricesFileArgIndex != -1) {
      LOG.info("Start of reading input prices file");
      try {
        // get filePath
        filePath = args[pricesFileArgIndex + 1];

        // temp collection of input lines
        List<String> tempInputLinesList = new ArrayList<>();

        // read input file
        Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine()) {
          tempInputLinesList.add(scanner.nextLine());
        }

        scanner.close();

        // validate input price file's content
        priceFileContentValidator.validate(tempInputLinesList);

        // fill prices cache
        fillPricesCache(tempInputLinesList);

      } catch (FileNotFoundException fileNotFoundException) {
        LOG.error("Input file: {} doesn't exists", filePath);
        throw fileNotFoundException;
      } catch (InvalidPricesFileFormatException invalidPricesFileFormatException) {
        LOG.error("Input file: {} has wrong format, numbers must be ordered from highest to lower.", filePath);
        throw invalidPricesFileFormatException;
      }

    }
  }

  private void fillPricesCache(List<String> tempInputLinesList) {
    for (int i = 0; i < tempInputLinesList.size(); i++) {
      String[] splitedLine = tempInputLinesList.get(i).split(SPACE_REGEXP);

      int nextIndex = i + 1;
      if (nextIndex < tempInputLinesList.size()) {
        String[] splitedLineOnNextIndex = tempInputLinesList.get(nextIndex).split(SPACE_REGEXP);

        BackfillPrice backfillPrice = new BackfillPrice(
          Double.parseDouble(splitedLine[0]),
          Double.parseDouble(splitedLineOnNextIndex[0]),
          Double.parseDouble(splitedLine[1]));

        pricesCache.getBackfillPriceList().add(backfillPrice);
      }
    }

  }

}


