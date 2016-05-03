package io.github.rscarvalho.rosettatest.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateValue implements FilterValue<LocalDate> {
  private final long duration;
  private final ChronoUnit chronoUnit;

  public DateValue(long duration, ChronoUnit chronoUnit) {
    this.duration = duration;
    this.chronoUnit = chronoUnit;
  }

  public long getDuration() {
    return duration;
  }

  public ChronoUnit getChronoUnit() {
    return chronoUnit;
  }

  @JsonIgnore
  public LocalDate getValue() {
    return null;
  }
}
