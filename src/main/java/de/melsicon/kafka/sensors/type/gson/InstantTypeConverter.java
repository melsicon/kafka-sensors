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
import java.time.Instant;

public final class InstantTypeConverter
    implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
  private static final BigDecimal INSTANT_MAX;
  private static final BigDecimal INSTANT_MIN;

  static {
    INSTANT_MAX = toBigDecimal(Instant.MAX.getEpochSecond(), Instant.MAX.getNano());
    INSTANT_MIN = toBigDecimal(Instant.MIN.getEpochSecond(), Instant.MIN.getNano());
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
  public JsonElement serialize(Instant src, Type srcType, JsonSerializationContext context) {
    var seconds = src.getEpochSecond();
    var nano = src.getNano();

    var result = toBigDecimal(seconds, nano);

    return new JsonPrimitive(result);
  }

  @Override
  public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
    var bd = json.getAsBigDecimal();
    if (bd.compareTo(INSTANT_MAX) > 0 || bd.compareTo(INSTANT_MIN) < 0) {
      throw new JsonParseException(String.format("Value %g out of range", bd));
    }
    if (bd.scale() <= 0) {
      return Instant.ofEpochSecond(bd.longValueExact());
    }
    var seconds = bd.longValue();
    var nanos = bd.subtract(BigDecimal.valueOf(seconds)).movePointRight(9).intValue();
    return Instant.ofEpochSecond(seconds, nanos);
  }
}
