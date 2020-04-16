package de.melsicon.kafka.serde.proto;

import com.google.protobuf.MessageLite;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ProtoSerializer<T extends MessageLite> implements Serializer<T> {
  @Override
  public byte @Nullable [] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    return message.toByteArray();
  }
}
