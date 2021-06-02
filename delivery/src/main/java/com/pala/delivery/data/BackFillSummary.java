package com.pala.delivery.data;

public class BackFillSummary {

  private Double weightSum;
  private Double feeSum;

  public BackFillSummary(Double weightSum, Double feeSum) {
    this.weightSum = weightSum;
    this.feeSum = feeSum;
  }

  public BackFillSummary(Double weightSum) {
    this.weightSum = weightSum;
    this.feeSum = null;
  }

  public Double getWeightSum() {
    return weightSum;
  }

  public void setWeightSum(Double weightSum) {
    this.weightSum = weightSum;
  }

  public Double getFeeSum() {
    return feeSum;
  }

  public void setFeeSum(Double feeSum) {
    this.feeSum = feeSum;
  }
}
