package de.melsicon.kafka.serde.confluent;

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
  @SuppressWarnings("nullness:argument.type.incompatible") // KafkaAvroDeserializer is not annotated
  public void configure(
      @Nullable Map<String, ?> deserializerConfig, boolean isDeserializerForRecordKeys) {
    inner.configure(deserializerConfig, isDeserializerForRecordKeys);
  }

  @Override
  @SuppressWarnings({
      "nullness:argument.type.incompatible",
      "nullness:override.return.invalid"
  }) // Deserializer and KafkaAvroDeserializer are not annotated
  public @Nullable GenericRecord deserialize(String topic, byte @Nullable [] bytes) {
    return (GenericRecord) inner.deserialize(topic, bytes, schema);
  }

  @Override
  public void close() {
    inner.close();
  }
}
