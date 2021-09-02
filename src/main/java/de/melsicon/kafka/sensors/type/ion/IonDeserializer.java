package de.melsicon.kafka.sensors.type.ion;

import com.amazon.ion.IonException;
import com.amazon.ion.system.IonReaderBuilder;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class IonDeserializer<T> implements Deserializer<T> {
  private final IonReaderBuilder builder;
  private final IonSerialReader<T> deserializer;

  public IonDeserializer(IonReaderBuilder builder, IonSerialReader<T> deserializer) {
    this.builder = builder;
    this.deserializer = deserializer;
  }

  @Override
  @SuppressWarnings("nullness:override.return") // Deserializer is not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] data) {
    if (data == null || data.length == 0) {
      return null;
    }

    try (var reader = builder.build(data)) {
      return deserializer.deserialize(reader);
    } catch (IOException | IonException e) {
      throw new SerializationException("Can't read record from topic " + topic, e);
    }
  }
}
