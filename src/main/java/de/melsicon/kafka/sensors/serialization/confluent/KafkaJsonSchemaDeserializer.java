package de.melsicon.kafka.sensors.serialization.confluent;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import java.util.Map;

/* package */ final class KafkaJsonSchemaDeserializer<T>
    extends io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer<T> {
  /* package */ KafkaJsonSchemaDeserializer(Class<T> type) {
    super();
    this.type = type;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    configure(new KafkaJsonSchemaDeserializerConfig(props), type);
  }
}
