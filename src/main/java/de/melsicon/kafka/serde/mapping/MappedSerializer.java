package de.melsicon.kafka.serde.mapping;

import java.util.Map;
import java.util.function.Function;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappedSerializer<U, T> implements Serializer<U> {
  private final Serializer<T> serializer;
  private final Function<U, T> unmapper;

  public MappedSerializer(Serializer<T> serializer, Function<U, T> unmapper) {
    this.serializer = serializer;
    this.unmapper = unmapper;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    serializer.configure(configs, isKey);
  }

  @Override
  public byte @Nullable [] serialize(String topic, @Nullable U data) {
    return serializer.serialize(topic, unmapper.apply(data));
  }

  @Override
  public byte @Nullable [] serialize(String topic, Headers headers, @Nullable U data) {
    return serializer.serialize(topic, headers, unmapper.apply(data));
  }

  @Override
  public void close() {
    serializer.close();
  }
}
