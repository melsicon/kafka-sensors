package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public final class JsonDeserializer<T> implements Deserializer<T> {
  private final ObjectReader objectReader;

  public JsonDeserializer(ObjectMapper objectMapper, Class<T> type) {
    this.objectReader = objectMapper.readerFor(type);
  }

  @Nullable
  @Override
  public T deserialize(String topic, @Nullable byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return objectReader.readValue(data);
    } catch (IOException e) {
      throw new SerializationException("Error while parsing message from topic " + topic, e);
    }
  }
}
