package de.melsicon.kafka.sensors.type.confluent.json;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import java.util.Map;

public final class KafkaJsonSchemaDeserializer<T>
    extends io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer<T> {
  public KafkaJsonSchemaDeserializer(Class<T> type) {
    super();
    this.type = type;
  }

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    configure(new KafkaJsonSchemaDeserializerConfig(props), type);
  }
}
