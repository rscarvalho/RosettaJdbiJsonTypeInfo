package io.github.rscarvalho.rosettatest.data;

public class StringValue implements FilterValue<String> {
  private final String value;

  public StringValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
