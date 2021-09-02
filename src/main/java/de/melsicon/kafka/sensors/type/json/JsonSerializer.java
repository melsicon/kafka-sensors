package de.melsicon.kafka.sensors.type.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class JsonSerializer<T> implements Serializer<T> {
  private final ObjectWriter objectWriter;

  public JsonSerializer(ObjectMapper objectMapper, Class<T> type) {
    this.objectWriter = objectMapper.writerFor(type);
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
    try {
      return objectWriter.writeValueAsBytes(message);
    } catch (IOException e) {
      var msg = String.format("Error while writing message %s for topic %s", message, topic);
      throw new SerializationException(msg, e);
    }
  }
}
