package de.melsicon.kafka.serde.confluent;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SpecificAvroDeserializer<T extends SpecificRecord> implements Deserializer<T> {
  private final Class<T> type;
  private final Schema schema;
  private final KafkaAvroDeserializer inner;

  public SpecificAvroDeserializer(Class<T> type) {
    this.type = type;
    this.schema = schemaForClass(type);

    this.inner = new KafkaAvroDeserializer();
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
        deserializerConfig == null
            ? new HashMap<>()
            : new HashMap<>((Map<String, ? extends Object>) deserializerConfig);
    specificAvroEnabledConfig.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    inner.configure(specificAvroEnabledConfig, isDeserializerForRecordKeys);
  }

  @Override
  @SuppressWarnings({
    "nullness:argument",
    "nullness:override.return"
  }) // Deserializer and KafkaAvroDeserializer are not annotated
  public @Nullable T deserialize(String topic, byte @Nullable [] bytes) {
    return type.cast(inner.deserialize(topic, bytes, schema));
  }

  @Override
  public void close() {
    inner.close();
  }
}
