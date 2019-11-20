package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.melsicon.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

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
  public @Nullable byte[] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    try {
      return objectWriter.writeValueAsBytes(message);
    } catch (IOException e) {
      throw new SerializationException(
          "Error while writing message " + message + " for topic " + topic, e);
    }
  }
}
