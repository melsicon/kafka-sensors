package de.melsicon.kafka.sensors.topology;

import de.melsicon.kafka.sensors.configuration.InputSerde;
import de.melsicon.kafka.sensors.configuration.KafkaConfiguration;
import de.melsicon.kafka.sensors.configuration.ResultSerde;
import de.melsicon.kafka.sensors.configuration.StoreSerde;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import javax.inject.Inject;
import javax.inject.Provider;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

/* package */ final class TopologyFactory {
  private final KafkaConfiguration configuration;
  private final Serde<SensorState> inputSerde;
  private final Serde<SensorState> storeSerde;
  private final Serde<SensorStateWithDuration> resultSerde;
  private final ValueTransformerSupplier<SensorState, SensorStateWithDuration> transformerSupplier;

  @Inject
  /* package */ TopologyFactory(
      KafkaConfiguration configuration,
      @InputSerde Serde<SensorState> inputSerde,
      @StoreSerde Serde<SensorState> storeSerde,
      @ResultSerde Serde<SensorStateWithDuration> resultSerde,
      Provider<DurationProcessor> processorProvider) {
    this.configuration = configuration;
    this.inputSerde = inputSerde;
    this.storeSerde = storeSerde;
    this.resultSerde = resultSerde;
    this.transformerSupplier = processorProvider::get;
  }

  /* package */ Topology createTopology() {
    var inputTopic = configuration.inputTopic();
    var consumed = Consumed.with(Serdes.String(), inputSerde);

    var resultTopic = configuration.resultTopic();
    var produced = Produced.with(Serdes.String(), resultSerde);

    var stateStore = stateStore();
    var stateStoreNames = new String[] {DurationProcessor.SENSOR_STATES};

    // Initialize Kafka Streams DSL
    var builder = new StreamsBuilder();

    // Register the store builder
    builder.addStateStore(stateStore);

    // Now, define our topology
    builder.stream(inputTopic, consumed)
        .transformValues(transformerSupplier, Named.as("DURATION-PROCESSOR"), stateStoreNames)
        .to(resultTopic, produced);

    return builder.build();
  }

  /**
   * Create a builder for our state store.
   *
   * @return Our state store builder
   */
  private StoreBuilder<KeyValueStore<String, SensorState>> stateStore() {
    return Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore(DurationProcessor.SENSOR_STATES),
        Serdes.String(),
        storeSerde);
  }
}
