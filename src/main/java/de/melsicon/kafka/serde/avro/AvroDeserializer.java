package de.melsicon.kafka.serde.avro;

import java.io.IOException;
import org.apache.avro.message.MessageDecoder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class AvroDeserializer<T> implements Deserializer<T> {
  private final MessageDecoder<T> decoder;

  public AvroDeserializer(MessageDecoder<T> decoder) {
    this.decoder = decoder;
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Deserializer is not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    try {
      return decoder.decode(data);
    } catch (IOException e) {
      var message = String.format("Error while parsing message from topic %s", topic);
      throw new SerializationException(message, e);
    }
  }
}
