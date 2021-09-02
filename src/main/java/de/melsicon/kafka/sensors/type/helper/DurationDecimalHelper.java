package de.melsicon.kafka.sensors.type.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public final class DurationDecimalHelper {
  private static final BigDecimal DURATION_MAX;
  private static final BigDecimal DURATION_MIN;

  static {
    DURATION_MAX = duration2Decimal(Duration.ofSeconds(Long.MAX_VALUE, 999_999_999));
    DURATION_MIN = duration2Decimal(Duration.ofSeconds(Long.MIN_VALUE, 0));
  }

  private DurationDecimalHelper() {}

  public static Duration decimal2Duration(BigDecimal seconds) {
    if (seconds.compareTo(DURATION_MAX) > 0 || seconds.compareTo(DURATION_MIN) < 0) {
      throw new IllegalArgumentException(String.format("Value %g out of range", seconds));
    }
    if (seconds.scale() <= 0) {
      return Duration.ofSeconds(seconds.longValueExact());
    }
    var secs = seconds.longValue();
    var nanos = seconds.subtract(BigDecimal.valueOf(secs)).movePointRight(9).longValue();
    return Duration.ofSeconds(secs, nanos);
  }

  public static BigDecimal duration2Decimal(Duration duration) {
    var seconds = duration.getSeconds();
    var nano = duration.getNano();
    var secs = BigDecimal.valueOf(seconds);
    if (nano == 0) {
      return secs.setScale(9, RoundingMode.UNNECESSARY);
    } else {
      return secs.add(BigDecimal.valueOf(nano, 9));
    }
  }
}
