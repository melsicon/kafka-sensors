package de.melsicon.kafka.sensors.serialization.avromapper;

import java.time.Duration;

/* package */ final class DurationMapper {
  private DurationMapper() {}

  /* package */ static Duration millis2Duration(long millis) {
    return Duration.ofMillis(millis);
  }

  /* package */ static long duration2Millis(Duration duration) {
    return duration.toMillis();
  }
}
