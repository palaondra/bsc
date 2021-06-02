package com.pala.delivery.cache;

import com.pala.delivery.data.BackfillPrice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Storing prices for backfills.
 */
@Component
public class PricesCache {

  private List<BackfillPrice> backfillPriceList = new ArrayList<>();

  public List<BackfillPrice> getBackfillPriceList() {
    return backfillPriceList;
  }

  /**
   * Return backfill's fee by his weight.
   *
   * @param backfillWeight Current backfill's weight.
   * @return Fee for this backfill.
   */
  public Double getFeeByBackfillWeight(Double backfillWeight) {
    Optional<BackfillPrice> backfillPriceOptional = backfillPriceList.stream()
      .filter(p -> (p.getMaxWeight() >= backfillWeight) && (backfillWeight > p.getMinWeight()))
      .findFirst();

    if (backfillPriceOptional.isPresent()) {
      return backfillPriceOptional.get().getFee();
    } else {
      return null;
    }
  }

}
