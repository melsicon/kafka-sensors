package de.melsicon.kafka.serde.confluent;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Deserializer;

public final class GenericAvroDeserializer implements Deserializer<GenericRecord> {
  private final KafkaAvroDeserializer inner;
  private final Schema schema;

  public GenericAvroDeserializer(Schema schema) {
    this.inner = new KafkaAvroDeserializer();
    this.schema = schema;
  }

  @Override
  public void configure(
      @Nullable Map<String, ?> deserializerConfig, boolean isDeserializerForRecordKeys) {
    inner.configure(deserializerConfig, isDeserializerForRecordKeys);
  }

  @Override
  public GenericRecord deserialize(String topic, byte[] bytes) {
    return (GenericRecord) inner.deserialize(topic, bytes, schema);
  }

  @Override
  public void close() {
    inner.close();
  }
}
