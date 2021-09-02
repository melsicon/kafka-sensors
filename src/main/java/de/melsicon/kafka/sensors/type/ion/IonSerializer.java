package de.melsicon.kafka.sensors.type.ion;

import com.amazon.ion.system.IonWriterBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class IonSerializer<T> implements Serializer<T> {
  private final IonWriterBuilder builder;
  private final IonSerialWriter<T> serializer;

  public IonSerializer(IonWriterBuilder builder, IonSerialWriter<T> serializer) {
    this.builder = builder;
    this.serializer = serializer;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    if (isKey) {
      throw new IllegalArgumentException("Ion encoding is not stable");
    }
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Serializer is not annotated
  public byte @Nullable [] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    try (var buffer = new ByteArrayOutputStream()) {
      try (var writer = builder.build(buffer)) {
        serializer.serialize(writer, message);
      }
      return buffer.toByteArray();
    } catch (IOException e) {
      throw new SerializationException("Can't write record to topic " + topic, e);
    }
  }
}
