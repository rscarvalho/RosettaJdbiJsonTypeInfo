package io.github.rscarvalho.rosettatest.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.rscarvalho.rosettatest.JsonUtils;

public class FilterTest {
  private static final String FILTER_JSON_VALUE = "{\"id\":0,\"name\":\"filter1\",\"filterValue\":{\"@type\":\"StringValue\",\"value\":\"my value\"}}";

  @Test
  public void itWritesToJsonProperly() throws JsonProcessingException {
    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);

    String filterString = JsonUtils.MAPPER.writeValueAsString(filter);
    assertThat(filterString).isEqualTo(FILTER_JSON_VALUE);
  }

  @Test
  public void itReadsFromJsonProperly() throws IOException {
    Filter filter = JsonUtils.MAPPER.readValue(FILTER_JSON_VALUE, Filter.class);
    assertThat(filter.getName()).isEqualTo(filter.getName());
    assertThat(filter.getId()).isEqualTo(filter.getId());
    assertThat(filter.getFilterValue()).isInstanceOf(StringValue.class);
  }
}
