package de.melsicon.kafka.topology;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import javax.inject.Singleton;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.Topology;

/** Provides the single {@link Topology} of our Kafka streams example. */
@Module
public abstract class TopologyModule {
  private TopologyModule() {}

  @Provides
  @Singleton
  /* package */ static Topology topology(
      @InputSerde Serde<SensorState> inputSerde,
      @StoreSerde Serde<SensorState> storeSerde,
      @ResultSerde Serde<SensorStateWithDuration> resultSerde,
      KafkaConfiguration configuration) {
    return TopologyFactory.createTopology(configuration, inputSerde, storeSerde, resultSerde);
  }
}
