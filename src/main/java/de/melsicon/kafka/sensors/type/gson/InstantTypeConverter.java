package de.melsicon.kafka.sensors.type.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.melsicon.kafka.sensors.type.helper.InstantDecimalHelper;
import java.lang.reflect.Type;
import java.time.Instant;

public final class InstantTypeConverter
    implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
  @Override
  public JsonElement serialize(Instant src, Type srcType, JsonSerializationContext context) {
    return new JsonPrimitive(InstantDecimalHelper.instant2Decimal(src));
  }

  @Override
  public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
    var decimal = json.getAsBigDecimal();
    return InstantDecimalHelper.decimal2Instant(decimal);
  }
}
