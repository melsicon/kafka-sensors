package de.melsicon.kafka.configuration;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import io.helidon.config.objectmapping.Value;
import java.util.List;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/** The main Kafka configuration. */
@Immutable
@AutoValue
public abstract class KafkaConfiguration {
  public static final String PREFIX = "kafka";

  /* package */ KafkaConfiguration() {}

  public static Builder builder() {
    return new AutoValue_KafkaConfiguration.Builder();
  }

  public abstract ImmutableList<String> bootstrapServers();

  public abstract String inputTopic();

  public abstract String resultTopic();

  public abstract Optional<String> clientID();

  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(@Nullable Object o);

  @AutoValue.Builder
  public abstract static class Builder {
    @Value(key = "brokers")
    public abstract Builder bootstrapServers(List<String> bootstrapServers);

    @Value(key = "input-topic")
    public abstract Builder inputTopic(String inputTopic);

    @Value(key = "result-topic")
    public abstract Builder resultTopic(String resultTopic);

    @Value(key = "client-id")
    public abstract Builder clientID(@Nullable String clientID);

    public abstract KafkaConfiguration build();
  }
}
