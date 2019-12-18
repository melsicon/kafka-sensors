package de.melsicon.kafka.serde.mapping;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Map;
import java.util.function.Function;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

public final class MappedDeserializer<U, T> implements Deserializer<U> {
  private final Deserializer<T> deserializer;
  private final Function<T, U> mapper;

  public MappedDeserializer(Deserializer<T> deserializer, Function<T, U> mapper) {
    this.deserializer = deserializer;
    this.mapper = mapper;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    deserializer.configure(configs, isKey);
  }

  @Override
  public U deserialize(String topic, @Nullable byte[] data) {
    return mapper.apply(deserializer.deserialize(topic, data));
  }

  @Override
  public U deserialize(String topic, Headers headers, @Nullable byte[] data) {
    return mapper.apply(deserializer.deserialize(topic, headers, data));
  }

  @Override
  public void close() {
    deserializer.close();
  }
}
