package de.melsicon.kafka.sensors.type.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

public final class InstantDecimalHelper {
  private static final BigDecimal INSTANT_MAX;
  private static final BigDecimal INSTANT_MIN;

  static {
    INSTANT_MAX = instant2Decimal(Instant.MAX);
    INSTANT_MIN = instant2Decimal(Instant.MIN);
  }

  private InstantDecimalHelper() {}

  public static Instant decimal2Instant(BigDecimal secondsFromEpoch) {
    if (secondsFromEpoch.compareTo(INSTANT_MAX) > 0
        || secondsFromEpoch.compareTo(INSTANT_MIN) < 0) {
      throw new IllegalArgumentException(String.format("Value %g out of range", secondsFromEpoch));
    }
    if (secondsFromEpoch.scale() <= 0) {
      return Instant.ofEpochSecond(secondsFromEpoch.longValueExact());
    }
    var seconds = secondsFromEpoch.longValue();
    var nanos =
        secondsFromEpoch.subtract(BigDecimal.valueOf(seconds)).movePointRight(9).longValue();
    return Instant.ofEpochSecond(seconds, nanos);
  }

  public static BigDecimal instant2Decimal(Instant instant) {
    var seconds = instant.getEpochSecond();
    var nano = instant.getNano();
    var decimal = BigDecimal.valueOf(seconds);
    if (nano == 0) {
      return decimal.setScale(9, RoundingMode.UNNECESSARY);
    } else {
      return decimal.add(BigDecimal.valueOf(nano, 9));
    }
  }
}
