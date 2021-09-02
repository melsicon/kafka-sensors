package de.melsicon.kafka.sensors.type.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class JsonDeserializer<T> implements Deserializer<T> {
  private final ObjectReader objectReader;

  public JsonDeserializer(ObjectMapper objectMapper, Class<T> type) {
    this.objectReader = objectMapper.readerFor(type);
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Deserializer is not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    try {
      return objectReader.readValue(data);
    } catch (IOException e) {
      var message = String.format("Error while parsing message from topic %s", topic);
      throw new SerializationException(message, e);
    }
  }
}
