package de.melsicon.kafka.serde.avro;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.IOException;
import org.apache.avro.message.MessageDecoder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public final class AvroDeserializer<T> implements Deserializer<T> {
  private final MessageDecoder<T> decoder;

  public AvroDeserializer(MessageDecoder<T> decoder) {
    this.decoder = decoder;
  }

  @Nullable
  @Override
  public T deserialize(String topic, @Nullable byte[] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    try {
      return decoder.decode(data, null);
    } catch (IOException e) {
      throw new SerializationException("Can't read record from topic " + topic, e);
    }
  }
}
