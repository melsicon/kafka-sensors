package de.melsicon.kafka.sensors.type.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.melsicon.kafka.sensors.type.helper.DurationDecimalHelper;
import java.lang.reflect.Type;
import java.time.Duration;

public final class DurationTypeConverter
    implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
  @Override
  public JsonElement serialize(Duration src, Type srcType, JsonSerializationContext context) {
    return new JsonPrimitive(DurationDecimalHelper.duration2Decimal(src));
  }

  @Override
  public Duration deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
    var decimal = json.getAsBigDecimal();
    return DurationDecimalHelper.decimal2Duration(decimal);
  }
}
