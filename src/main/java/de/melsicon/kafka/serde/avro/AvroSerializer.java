package de.melsicon.kafka.serde.avro;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.message.MessageEncoder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public final class AvroSerializer<T> implements Serializer<T> {
  private final MessageEncoder<T> encoder;

  public AvroSerializer(MessageEncoder<T> encoder) {
    this.encoder = encoder;
  }

  @Nullable
  @Override
  public byte[] serialize(String topic, @Nullable T data) {
    if (data == null) {
      return null;
    }
    try (var buffer = new ByteArrayOutputStream()) {
      encoder.encode(data, buffer);
      return buffer.toByteArray();
    } catch (IOException e) {
      throw new SerializationException("Can't write record to topic " + topic, e);
    }
  }
}
