package de.melsicon.kafka.serde.avro;

import de.melsicon.annotation.Nullable;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public final class SpecificAvroDeserializer<T extends SpecificRecord> implements Deserializer<T> {
  private final KafkaAvroDeserializer inner;
  private final Schema schema;

  public SpecificAvroDeserializer(Schema schema) {
    this.inner = new KafkaAvroDeserializer();
    this.schema = schema;
  }

  public SpecificAvroDeserializer(Class<T> type) {
    this(schemaForClass(type));
  }

  private static <T extends SpecificRecord> Schema schemaForClass(Class<T> type) {
    T t;
    try {
      t = type.getDeclaredConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new SerializationException("Can't read schema from class " + type.getName(), e);
    }

    return t.getSchema();
  }

  @Override
  public void configure(
      @Nullable Map<String, ?> deserializerConfig, boolean isDeserializerForRecordKeys) {
    Map<String, Object> specificAvroEnabledConfig =
        deserializerConfig == null ? new HashMap<>() : new HashMap<>(deserializerConfig);
    specificAvroEnabledConfig.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    inner.configure(specificAvroEnabledConfig, isDeserializerForRecordKeys);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T deserialize(String topic, byte[] bytes) {
    return (T) inner.deserialize(topic, bytes, schema);
  }

  @Override
  public void close() {
    inner.close();
  }
}
