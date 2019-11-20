package de.melsicon.kafka.topology;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;

public final class TopologyFactory {
  @Inject
  public TopologyFactory() {}

  public Topology createTopology(
      String inputTopic,
      String resultTopic,
      Serde<SensorState> inputSerde,
      Serde<SensorState> storeSerde,
      Serde<SensorStateWithDuration> resultSerde) {
    var builder = new StreamsBuilder();

    // Our state store
    var storeBuilder =
        Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore(DurationProcessor.SENSOR_STATES),
            Serdes.String(),
            storeSerde);

    // Register the store builder
    builder.addStateStore(storeBuilder);

    builder.stream(inputTopic, Consumed.with(Serdes.String(), inputSerde))
        .transformValues(DurationProcessor::new, DurationProcessor.SENSOR_STATES)
        .to(resultTopic, Produced.with(Serdes.String(), resultSerde));

    return builder.build();
  }
}
