package de.melsicon.kafka.sensors.type.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public final class DurationTypeConverter
    implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
  private static final BigDecimal DURATION_MAX;
  private static final BigDecimal DURATION_MIN;

  static {
    DURATION_MAX = toBigDecimal(Long.MAX_VALUE, 999_999_999);
    DURATION_MIN = toBigDecimal(Long.MIN_VALUE, 0);
  }

  private static BigDecimal toBigDecimal(long seconds, int nano) {
    var s = BigDecimal.valueOf(seconds);
    BigDecimal result;
    if (nano == 0) {
      result = s.setScale(9, RoundingMode.UNNECESSARY);
    } else {
      result = s.add(BigDecimal.valueOf(nano, 9));
    }
    return result;
  }

  @Override
  public JsonElement serialize(Duration src, Type srcType, JsonSerializationContext context) {
    var seconds = src.getSeconds();
    var nano = src.getNano();
    return new JsonPrimitive(toBigDecimal(seconds, nano));
  }

  @Override
  public Duration deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
    var bd = json.getAsBigDecimal();
    if (bd.compareTo(DURATION_MAX) > 0 || bd.compareTo(DURATION_MIN) < 0) {
      throw new JsonParseException(String.format("Value %g out of range", bd));
    }
    if (bd.scale() <= 0) {
      return Duration.ofSeconds(bd.longValueExact());
    }
    var seconds = bd.longValue();
    var nanos = bd.subtract(BigDecimal.valueOf(seconds)).movePointRight(9).intValue();
    return Duration.ofSeconds(seconds, nanos);
  }
}
