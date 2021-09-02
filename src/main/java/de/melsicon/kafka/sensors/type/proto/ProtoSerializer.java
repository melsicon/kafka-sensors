package de.melsicon.kafka.sensors.type.proto;

import com.google.protobuf.MessageLite;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ProtoSerializer<T extends MessageLite> implements Serializer<T> {
  @Override
  @SuppressWarnings("nullness:override.return") // Serializer is not annotated
  public byte @Nullable [] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    return message.toByteArray();
  }
}
