package de.melsicon.kafka.sensors.serialization.gson;

import com.google.gson.TypeAdapter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class GsonDeserializer<T> implements Deserializer<T> {
  private final TypeAdapter<T> adapter;

  /* package */ GsonDeserializer(TypeAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Deserializer is not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    try {
      var string = new String(data, StandardCharsets.UTF_8);
      return adapter.fromJson(string);
    } catch (IOException e) {
      var message = String.format("Error while parsing message from topic %s", topic);
      throw new SerializationException(message, e);
    }
  }
}
