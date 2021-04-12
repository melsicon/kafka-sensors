package de.melsicon.kafka.streams;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG;

import com.google.common.util.concurrent.AbstractIdleService;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;

/** Configures, starts and shuts down our {@link KafkaStreams}. */
@Singleton
/* package */ final class MyStreams extends AbstractIdleService {
  private static final String DEFAULT_CLIENT_ID = "default";

  private static final String KEY_SERIALIZER_ENCODING = "key.serializer.encoding";
  private static final String KEY_DESERIALIZER_ENCODING = "key.deserializer.encoding";

  private final KafkaStreams kafkaStreams;

  @Inject
  /* package */ MyStreams(Topology topology, KafkaConfiguration configuration) {
    var settings = new Properties();
    copySettings(configuration, settings);

    // https://kafka.apache.org/documentation/streams/developer-guide/config-streams.html#default-deserialization-exception-handler
    settings.put(
        DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
        LogAndContinueExceptionHandler.class);
    // https://kafka.apache.org/documentation/streams/developer-guide/config-streams.html#default-production-exception-handler
    settings.put(
        DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG,
        ContinueProductionExceptionHandler.class);

    kafkaStreams = new KafkaStreams(topology, settings);
  }

  private static void copySettings(KafkaConfiguration configuration, Properties settings) {
    var clientID = configuration.clientID().orElse(DEFAULT_CLIENT_ID);
    var brokers = String.join(",", configuration.bootstrapServers());

    settings.setProperty(APPLICATION_ID_CONFIG, clientID);
    settings.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers);
    settings.setProperty(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    settings.setProperty(KEY_SERIALIZER_ENCODING, US_ASCII.name());
    settings.setProperty(KEY_DESERIALIZER_ENCODING, US_ASCII.name());
  }

  @Override
  protected void startUp() {
    kafkaStreams.start();
  }

  @Override
  protected void shutDown() {
    kafkaStreams.close();
  }
}
