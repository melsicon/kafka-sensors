package de.melsicon.kafka.sensors.type.confluent.generic;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GenericAvroDeserializer implements Deserializer<GenericRecord> {
  private final KafkaAvroDeserializer inner;
  private final Schema schema;

  public GenericAvroDeserializer(Schema schema) {
    this.inner = new KafkaAvroDeserializer();
    this.schema = schema;
  }

  @Override
  @SuppressWarnings("nullness:argument") // KafkaAvroDeserializer is not annotated
  public void configure(@Nullable Map<String, ?> configs, boolean isKey) {
    inner.configure(configs, isKey);
  }

  @Override
  @SuppressWarnings({
    "nullness:argument",
    "nullness:override.return"
  }) // Deserializer and KafkaAvroDeserializer are not annotated
  public @Nullable GenericRecord deserialize(String topic, byte @Nullable [] bytes) {
    return (GenericRecord) inner.deserialize(topic, bytes, schema);
  }

  @Override
  public void close() {
    inner.close();
  }
}
