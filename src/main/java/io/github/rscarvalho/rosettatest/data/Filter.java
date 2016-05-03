package io.github.rscarvalho.rosettatest.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.rosetta.annotations.StoredAsJson;

public class Filter {
  private final long id;
  private final String name;
  private final FilterValue<?> filterValue;

  @JsonCreator
  public Filter(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("filterValue") FilterValue<?> filterValue) {
    this.id = id;
    this.name = name;
    this.filterValue = filterValue;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @StoredAsJson
  public FilterValue<?> getFilterValue() {
    return filterValue;
  }
}
