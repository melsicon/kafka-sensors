package de.melsicon.kafka.serde.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ProtoDeserializer<T extends MessageLite> implements Deserializer<T> {
  private final Parser<T> parser;

  public ProtoDeserializer(Parser<T> parser) {
    this.parser = parser;
  }

  @Override
  @SuppressWarnings("nullness:override.return.invalid") // Deserializer is not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] data) {
    if (data == null || data.length == 0) {
      return null;
    }
    try {
      return parser.parseFrom(data);
    } catch (InvalidProtocolBufferException e) {
      var message = String.format("Error while parsing message from topic %s", topic);
      throw new SerializationException(message, e);
    }
  }
}
