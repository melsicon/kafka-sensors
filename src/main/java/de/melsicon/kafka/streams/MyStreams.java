package de.melsicon.kafka.streams;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG;

import com.google.common.util.concurrent.AbstractIdleService;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.topology.TopologyFactory;
import java.util.Properties;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;

@Singleton
public final class MyStreams extends AbstractIdleService {
  private static final String DEFAULT_CLIENT_ID = "default";

  private static final String KEY_SERIALIZER_ENCODING = "key.serializer.encoding";
  private static final String KEY_DESERIALIZER_ENCODING = "key.deserializer.encoding";

  private final KafkaStreams kafkaStreams;

  @Inject
  MyStreams(
      TopologyFactory topologyFactory,
      KafkaConfiguration configuration,
      @InputSerde Supplier<Serde<SensorState>> inputSerde,
      @StoreSerde Supplier<Serde<SensorState>> storeSerde,
      @ResultSerde Supplier<Serde<SensorStateWithDuration>> resultSerde) {

    var topology =
        topologyFactory.createTopology(
            configuration.inputTopic(),
            configuration.resultTopic(),
            inputSerde.get(),
            storeSerde.get(),
            resultSerde.get());

    var properties = properties(configuration);

    var settings = new Properties();
    settings.putAll(properties);

    settings.put(
        DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
        ContinueDeserializationExceptionHandler.class);
    settings.put(
        DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG,
        ContinueProductionExceptionHandler.class);

    kafkaStreams = new KafkaStreams(topology, settings);
  }

  private static Properties properties(KafkaConfiguration configuration) {
    var clientID = configuration.clientID().orElse(DEFAULT_CLIENT_ID);
    var brokers = String.join(",", configuration.bootstrapServers());

    var props = new Properties();
    props.setProperty(APPLICATION_ID_CONFIG, clientID);
    props.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.setProperty(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    props.setProperty(KEY_SERIALIZER_ENCODING, US_ASCII.name());
    props.setProperty(KEY_DESERIALIZER_ENCODING, US_ASCII.name());
    return props;
  }

  @Override
  protected void startUp() throws Exception {
    kafkaStreams.start();
  }

  @Override
  protected void shutDown() throws Exception {
    kafkaStreams.close();
  }
}
