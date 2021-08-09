package de.melsicon.kafka.sensors.topology;

import de.melsicon.kafka.sensors.configuration.KafkaConfiguration;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;

/* package */ final class TopologyFactory {
  private TopologyFactory() {}

  /* package */ static Topology createTopology(
      KafkaConfiguration configuration,
      Serde<SensorState> inputSerde,
      Serde<SensorState> storeSerde,
      Serde<SensorStateWithDuration> resultSerde) {
    // Create a builder for our state store
    var storeBuilder =
        Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore(DurationProcessor.SENSOR_STATES),
            Serdes.String(),
            storeSerde);

    // Initialize Kafka Streams DSL
    var builder = new StreamsBuilder();

    // Register the store builder
    builder.addStateStore(storeBuilder);

    var stateStoreNames = new String[] {DurationProcessor.SENSOR_STATES};

    builder.stream(configuration.inputTopic(), Consumed.with(Serdes.String(), inputSerde))
        .transformValues(
            DurationProcessor.supplier(), Named.as("DURATION-PROCESSOR"), stateStoreNames)
        .to(configuration.resultTopic(), Produced.with(Serdes.String(), resultSerde));

    return builder.build();
  }
}
