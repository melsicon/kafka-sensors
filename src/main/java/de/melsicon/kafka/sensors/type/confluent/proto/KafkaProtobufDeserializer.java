package de.melsicon.kafka.sensors.type.confluent.proto;

import com.google.protobuf.Message;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import java.util.Map;

public final class KafkaProtobufDeserializer<T extends Message>
    extends io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer<T> {
  public KafkaProtobufDeserializer(Class<T> type) {
    super();
    this.specificProtobufClass = type;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    configure(new KafkaProtobufDeserializerConfig(props), specificProtobufClass);
  }
}
