package de.melsicon.kafka.context;

import dagger.Module;
import de.melsicon.kafka.streams.StreamsModule;
import de.melsicon.kafka.topology.TopologyModule;

/** Provides the bindings for our main application. */
@Module(
    includes = {
      ConfigurationModule.class,
      SerdesModule.class,
      StartUpModule.class,
      StreamsModule.class,
      TopologyModule.class
    })
/* package */ abstract class MainModule {
  private MainModule() {}
}
