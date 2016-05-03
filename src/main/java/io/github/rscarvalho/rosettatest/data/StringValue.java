package io.github.rscarvalho.rosettatest.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StringValue implements FilterValue<String> {
  private final String value;

  @JsonCreator
  public StringValue(@JsonProperty("value") String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
