package de.melsicon.kafka.sensors.serialization.confluent;

import com.google.protobuf.Message;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import java.util.Map;

/* package */ final class KafkaProtobufDeserializer<T extends Message>
    extends io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer<T> {
  /* package */ KafkaProtobufDeserializer(Class<T> type) {
    super();
    this.specificProtobufClass = type;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    configure(new KafkaProtobufDeserializerConfig(props), specificProtobufClass);
  }
}
