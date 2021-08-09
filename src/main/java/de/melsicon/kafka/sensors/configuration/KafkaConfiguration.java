package de.melsicon.kafka.sensors.configuration;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import io.helidon.config.objectmapping.Value;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.immutables.value.Value.Style;

/** The main Kafka configuration. */
@Immutable
@Style(
    optionalAcceptNullable = true,
    passAnnotations = {Immutable.class})
@org.immutables.value.Value.Immutable
public abstract class KafkaConfiguration implements WithKafkaConfiguration {
  public static final String PREFIX = "kafka";

  /* package */ KafkaConfiguration() {}

  public static Builder builder() {
    return ImmutableKafkaConfiguration.builder();
  }

  public abstract ImmutableList<String> bootstrapServers();

  public abstract String inputTopic();

  public abstract String resultTopic();

  public abstract Optional<String> clientID();

  public abstract static class Builder {
    @Value(key = "brokers")
    public abstract Builder bootstrapServers(Iterable<String> bootstrapServers);

    @Value(key = "input-topic")
    public abstract Builder inputTopic(String inputTopic);

    @Value(key = "result-topic")
    public abstract Builder resultTopic(String resultTopic);

    @Value(key = "client-id")
    public abstract Builder clientID(@Nullable String clientID);

    public abstract KafkaConfiguration build();
  }
}
