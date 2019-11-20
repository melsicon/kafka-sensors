package de.melsicon.kafka.configuration;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import io.helidon.config.Config;
import io.helidon.config.objectmapping.Value;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

@Immutable
@AutoValue
public abstract class KafkaConfiguration {
  private static final String PREFIX = "kafka";

  /* package */ KafkaConfiguration() {}

  public static KafkaConfiguration extract(Config config) {
    var subConfig = config.get(PREFIX);
    return subConfig.as(KafkaConfiguration.class).get();
  }

  public static Builder builder() {
    return new AutoValue_KafkaConfiguration.Builder();
  }

  public abstract ImmutableList<String> bootstrapServers();

  public abstract String inputTopic();

  public abstract String resultTopic();

  public abstract Optional<String> clientID();

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
