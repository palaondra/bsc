package com.pala.delivery.cache;

import com.pala.delivery.data.BackFillSummary;
import com.pala.delivery.data.Backfill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Store all backfills in memory.
 */
@Component
public class BackfillCache {

  @Autowired
  private PricesCache pricesCache;

  /**
   * Key - post code
   * Value - BackFillSummary entity - stores SUM of weights and fees.
   */
  private Map<String, BackFillSummary> backfillWeightOnPostCodeMap = new ConcurrentHashMap<>();

  public Map<String, BackFillSummary> getBackfillWeightOnPostCodeMap() {
    return backfillWeightOnPostCodeMap;
  }

  /**
   * Add new Backfill entity to cache.
   *
   * @param backfill Incoming Backfill entity.
   */
  public void addBackfill(Backfill backfill) {
    // try to find fee for this backfill
    Double fee = pricesCache.getFeeByBackfillWeight(backfill.getWeight());

    // incoming backfill entity is new
    if (!backfillWeightOnPostCodeMap.containsKey(backfill.getDestination())) {

      BackFillSummary backFillSummary;
      if (!ObjectUtils.isEmpty(fee)) {
        backFillSummary = new BackFillSummary(backfill.getWeight(), fee);
      } else {
        backFillSummary = new BackFillSummary(backfill.getWeight());
      }

      backfillWeightOnPostCodeMap.put(
        backfill.getDestination(),
        backFillSummary);

    } else {
      // post code already exists in cache => update sum of backfill weights

      // get current BackfillSummary entity
      BackFillSummary backFillSummary = backfillWeightOnPostCodeMap.get(backfill.getDestination());

      // calculate SUM of weights
      double newWeight = Double.sum(
        backfillWeightOnPostCodeMap.get(backfill.getDestination()).getWeightSum(),
        backfill.getWeight());
      backFillSummary.setWeightSum(newWeight);

      // calculate SUM of fees
      if (!ObjectUtils.isEmpty(backFillSummary.getFeeSum()) && !ObjectUtils.isEmpty(fee)) {
        double newFee = Double.sum(
          backfillWeightOnPostCodeMap.get(backfill.getDestination()).getFeeSum(),
          fee);
        backFillSummary.setFeeSum(newFee);
      }

      backfillWeightOnPostCodeMap.put(backfill.getDestination(), backFillSummary);

    }
  }

}
