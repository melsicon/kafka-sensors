package de.melsicon.kafka.serde.proto;

import com.google.protobuf.MessageLite;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

/* package */ final class ProtoSerializer<T extends MessageLite> implements Serializer<T> {
  @Override
  @SuppressWarnings("nullness:override.return") // Serializer is not annotated
  public byte @Nullable [] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    return message.toByteArray();
  }
}
