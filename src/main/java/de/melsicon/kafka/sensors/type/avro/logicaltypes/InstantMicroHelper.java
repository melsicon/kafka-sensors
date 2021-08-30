package de.melsicon.kafka.sensors.type.avro.logicaltypes;

import java.time.Instant;

public final class InstantMicroHelper {
  private static final long MICROS_PER_SECOND = 1_000_000L;
  private static final long NANOS_PER_MICROS = 1_000L;

  private InstantMicroHelper() {}

  public static Instant fromLong(long microsFromEpoch) {
    var epochSecond = microsFromEpoch / MICROS_PER_SECOND;
    var nanoAdjustment = microsFromEpoch % MICROS_PER_SECOND * NANOS_PER_MICROS;

    return Instant.ofEpochSecond(epochSecond, nanoAdjustment);
  }

  public static Long toLong(Instant instant) {
    var seconds = instant.getEpochSecond();
    var nanos = instant.getNano();

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
