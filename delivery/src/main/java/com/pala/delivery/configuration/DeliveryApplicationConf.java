package com.pala.delivery.configuration;

import com.pala.delivery.cache.BackfillCache;
import com.pala.delivery.cache.PricesCache;
import com.pala.delivery.data.BackfillFactory;
import com.pala.delivery.file_reader.InputBackfillFileReader;
import com.pala.delivery.file_reader.InputPricesFileReader;
import com.pala.delivery.printer.BackfillPrinter;
import com.pala.delivery.validator.BackfillValidator;
import com.pala.delivery.validator.InputArgumentValidator;
import com.pala.delivery.validator.InputLineValidator;
import com.pala.delivery.validator.PriceFileContentValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class DeliveryApplicationConf {

  @Bean
  public BackfillFactory backfillFactory() {
    return new BackfillFactory();
  }

  @Bean
  public BackfillValidator backfillValidator() {
    return new BackfillValidator();
  }

  @Bean
  public InputLineValidator inputLineValidator() {
    return new InputLineValidator();
  }

  @Bean
  public BackfillPrinter backfillProcessor() {
    return new BackfillPrinter();
  }

  @Bean
  public BackfillCache backfillCache() {
    return new BackfillCache();
  }

  @Bean
  public InputArgumentValidator inputArgumentValidator() {
    return new InputArgumentValidator();
  }

  @Bean
  public InputBackfillFileReader inputBackfillFileReader() {
    return new InputBackfillFileReader();
  }

  @Bean
  public InputPricesFileReader inputPricesFileReader() {
    return new InputPricesFileReader();
  }

  @Bean
  public PricesCache pricesCache() {
    return new PricesCache();
  }

  @Bean
  public PriceFileContentValidator priceFileContentValidator() {
    return new PriceFileContentValidator();
  }

}
