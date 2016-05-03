package io.github.rscarvalho.rosettatest.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberValue implements FilterValue<Double> {
  private final double value;

  @JsonCreator
  public NumberValue(@JsonProperty("value") double value) {
    this.value = value;
  }

  @JsonProperty
  public Double getValue() {
    return value;
  }
}
