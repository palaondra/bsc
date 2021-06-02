package com.pala.delivery.data;

public class BackfillPrice {

  private Double maxWeight;
  private Double minWeight;
  private Double fee;

  public BackfillPrice(Double maxWeight, Double minWeight, Double fee) {
    this.maxWeight = maxWeight;
    this.minWeight = minWeight;
    this.fee = fee;
  }

  public Double getMaxWeight() {
    return maxWeight;
  }

  public void setMaxWeight(Double maxWeight) {
    this.maxWeight = maxWeight;
  }

  public Double getMinWeight() {
    return minWeight;
  }

  public void setMinWeight(Double minWeight) {
    this.minWeight = minWeight;
  }

  public Double getFee() {
    return fee;
  }

  public void setFee(Double fee) {
    this.fee = fee;
  }
}
