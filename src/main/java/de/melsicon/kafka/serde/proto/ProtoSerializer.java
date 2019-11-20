package de.melsicon.kafka.serde.proto;

import com.google.protobuf.MessageLite;
import de.melsicon.annotation.Nullable;
import org.apache.kafka.common.serialization.Serializer;

public final class ProtoSerializer<T extends MessageLite> implements Serializer<T> {
  @Override
  public @Nullable byte[] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    return message.toByteArray();
  }
}
