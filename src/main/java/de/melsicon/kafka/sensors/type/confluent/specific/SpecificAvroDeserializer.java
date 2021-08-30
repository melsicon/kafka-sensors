package de.melsicon.kafka.sensors.type.confluent.specific;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SpecificAvroDeserializer<T extends GenericContainer> implements Deserializer<T> {
  private final Class<T> type;
  private final Schema schema;
  private final KafkaAvroDeserializer inner;

  public SpecificAvroDeserializer(Class<T> type) {
    this.type = type;
    this.schema = schemaForClass(type);

    this.inner = new KafkaAvroDeserializer();
  }

  private static <T extends GenericContainer> Schema schemaForClass(Class<T> type) {
    T t;
    try {
      t = type.getDeclaredConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new SerializationException("Can't read schema from class " + type.getName(), e);
    }

    return t.getSchema();
  }

  @Override
  @SuppressWarnings("nullness:override.param") // Deserializer is not annotated
  public void configure(@Nullable Map<String, ? extends @NonNull Object> configs, boolean isKey) {
    Map<String, Object> specificAvroEnabledConfig =
        configs == null ? new HashMap<>() : new HashMap<>(configs);
    specificAvroEnabledConfig.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    inner.configure(specificAvroEnabledConfig, isKey);
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
