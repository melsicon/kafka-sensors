package de.melsicon.kafka.serde.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public final class ProtoDeserializer<T extends MessageLite> implements Deserializer<T> {
  private final Parser<T> parser;

  public ProtoDeserializer(Parser<T> parser) {
    this.parser = parser;
  }

  @Nullable
  @Override
  public T deserialize(String topic, @Nullable byte[] data) {
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
