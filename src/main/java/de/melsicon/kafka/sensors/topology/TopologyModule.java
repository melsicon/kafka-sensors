package de.melsicon.kafka.sensors.topology;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.apache.kafka.streams.Topology;

/** Provides the single {@link Topology} of our Kafka streams example. */
@Module
public abstract class TopologyModule {
  private TopologyModule() {}

  @Provides
  @Singleton
  /* package */ static Topology topology(TopologyFactory factory) {
    return factory.createTopology();
  }
}
