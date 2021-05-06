package de.melsicon.kafka.serde.mapping;

import java.util.Map;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappedDeserializer<U, T> implements Deserializer<U> {
  private final Deserializer<T> deserializer;
  private final MapFunction<T, U> mapper;

  public MappedDeserializer(Deserializer<T> deserializer, MapFunction<T, U> mapper) {
    this.deserializer = deserializer;
    this.mapper = mapper;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    deserializer.configure(configs, isKey);
  }

  @Override
  @SuppressWarnings({
    "nullness:argument",
    "nullness:override.return"
  }) // Deserializer is not annotated
  public @Nullable U deserialize(String topic, byte @Nullable [] data) {
    return mapper.apply(deserializer.deserialize(topic, data));
  }

  @Override
  @SuppressWarnings({
    "nullness:argument",
    "nullness:override.return"
  }) // Deserializer is not annotated
  public @Nullable U deserialize(String topic, Headers headers, byte @Nullable [] data) {
    return mapper.apply(deserializer.deserialize(topic, headers, data));
  }

  @Override
  public void close() {
    deserializer.close();
  }
}
