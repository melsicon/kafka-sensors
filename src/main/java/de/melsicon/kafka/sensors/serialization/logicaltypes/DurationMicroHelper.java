package de.melsicon.kafka.sensors.serialization.logicaltypes;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public final class DurationMicroHelper {
  private static final long MICROS_PER_SECOND = 1_000_000L;
  private static final long NANOS_PER_MICROS = 1_000L;

  private DurationMicroHelper() {}

  public static Duration fromLong(long micros) {
    return Duration.of(micros, ChronoUnit.MICROS);
  }

  public static Long toLong(Duration duration) {
    var seconds = duration.getSeconds();
    var nanos = duration.getNano();
    if (seconds < 0 && nanos > 0) {
      var micros = Math.multiplyExact(seconds + 1, MICROS_PER_SECOND);
      var adjustment = nanos / NANOS_PER_MICROS - MICROS_PER_SECOND;

      return Math.addExact(micros, adjustment);
    } else {
      var micros = Math.multiplyExact(seconds, MICROS_PER_SECOND);

      return Math.addExact(micros, nanos / NANOS_PER_MICROS);
    }
  }
}
