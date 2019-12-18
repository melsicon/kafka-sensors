package de.melsicon.kafka.serde.proto;

import com.google.protobuf.MessageLite;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.kafka.common.serialization.Serializer;

public final class ProtoSerializer<T extends MessageLite> implements Serializer<T> {
  @Nullable
  @Override
  public byte[] serialize(String topic, @Nullable T message) {
    if (message == null) {
      return null;
    }
    return message.toByteArray();
  }
}
