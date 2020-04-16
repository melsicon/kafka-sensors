package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({
  "nullness:argument.type.incompatible",
  "nullness:override.return.invalid"
}) // Deserializer is not annotated
public final class JsonDeserializer<T> implements Deserializer<T> {
  private final ObjectReader objectReader;

  public JsonDeserializer(ObjectMapper objectMapper, Class<T> type) {
    this.objectReader = objectMapper.readerFor(type);
  }

  @Override
  @Nullable
  public T deserialize(String topic, byte @Nullable [] data) {
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
