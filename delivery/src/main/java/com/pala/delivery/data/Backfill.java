package com.pala.delivery.data;

public class Backfill {

  private Double weight;
  private String destination;

  public Backfill(Double weight, String destination) {
    this.weight = weight;
    this.destination = destination;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }
}
