package de.melsicon.kafka.sensors.type.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.melsicon.kafka.sensors.type.gson.SensorStateBase.State;
import java.lang.reflect.Type;
import java.util.Locale;

public final class StateTypeConverter implements JsonSerializer<State>, JsonDeserializer<State> {

  @Override
  public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    return State.valueOf(json.getAsString().toUpperCase(Locale.ROOT));
  }

  @Override
  public JsonElement serialize(State src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.name().toLowerCase(Locale.ROOT));
  }
}
