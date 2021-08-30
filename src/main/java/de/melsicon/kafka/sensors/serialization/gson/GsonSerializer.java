package de.melsicon.kafka.sensors.serialization.gson;

import com.google.gson.TypeAdapter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class GsonSerializer<T> implements Serializer<T> {
  private final TypeAdapter<T> adapter;

  /* package */ GsonSerializer(TypeAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    if (isKey) {
      throw new IllegalArgumentException("JSON encoding is not stable");
    }
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Serializer is not annotated
  public byte @Nullable [] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    try (var out = new StringWriter()) {
      adapter.toJson(out, message);
      return out.toString().getBytes(StandardCharsets.UTF_8);
    } catch (IOException e) {
      var msg = String.format("Error while writing message %s for topic %s", message, topic);
      throw new SerializationException(msg, e);
    }
  }
}
