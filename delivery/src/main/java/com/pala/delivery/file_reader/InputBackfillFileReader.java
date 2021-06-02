package com.pala.delivery.file_reader;

import com.pala.delivery.cache.BackfillCache;
import com.pala.delivery.data.Backfill;
import com.pala.delivery.data.BackfillFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.IntStream;

import static com.pala.delivery.common.CommonConstants.BACKFILL_FILE_INPUT_ARGUMENT;

/**
 * Read input file with backfills. This file has this structure:
 * packageWeight postCode
 */
@Component
public class InputBackfillFileReader {

  private static Logger LOG = LoggerFactory.getLogger(InputBackfillFileReader.class);

  @Autowired
  private BackfillFactory backfillFactory;

  @Autowired
  private BackfillCache backfillCache;

  /**
   * Parse path to backfill's file from input <code>args</code> and read it.
   *
   * @param args Input <code>args</code>
   * @throws FileNotFoundException
   */
  public void readFile(String... args) throws FileNotFoundException {
    String filePath = "";

    // find -filename argument's index
    int fileNameArgIndex = IntStream.range(0, args.length)
      .filter(fileNameArg -> BACKFILL_FILE_INPUT_ARGUMENT.equals(args[fileNameArg]))
      .findFirst()
      .orElse(-1);

    if (fileNameArgIndex != -1) {
      LOG.info("Start of reading input backfill file");
      try {
        // get filePath
        filePath = args[fileNameArgIndex + 1];

        // read input file
        Scanner scanner = new Scanner(new File(filePath));

        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          Backfill backfill = backfillFactory.getBackfill(line);
          if (!ObjectUtils.isEmpty(backfill)) {
            backfillCache.addBackfill(backfill);
          }
        }

        scanner.close();

      } catch (FileNotFoundException fileNotFoundException) {
        LOG.error("Input file: {} doesn't exists", filePath);
        throw fileNotFoundException;
      }

    }
  }

}
