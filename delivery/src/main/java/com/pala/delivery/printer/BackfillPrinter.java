package com.pala.delivery.printer;

import com.pala.delivery.cache.BackfillCache;
import com.pala.delivery.data.BackFillSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Component
public class BackfillPrinter {

  private static Logger LOG = LoggerFactory.getLogger(BackfillPrinter.class);

  @Autowired
  private BackfillCache backfillCache;

  @Scheduled(cron = "0 * * * * *")
  public void showBackfillSummary() {
    LOG.info("Printing packages");
    for (Map.Entry<String, BackFillSummary> entry : backfillCache.getBackfillWeightOnPostCodeMap().entrySet()) {
      LOG.info(
        "PostCode: {} SUM of weight: {} SUM of fee: {}",
        entry.getKey(),
        entry.getValue().getWeightSum(),
        !ObjectUtils.isEmpty(entry.getValue().getFeeSum()) ? entry.getValue().getFeeSum() : "");
    }
    LOG.info("--------------------------------------------------------");
  }


}
