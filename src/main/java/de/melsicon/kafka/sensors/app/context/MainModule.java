package de.melsicon.kafka.sensors.app.context;

import dagger.Module;
import de.melsicon.kafka.sensors.serde.context.AppSerDesModule;
import de.melsicon.kafka.sensors.streams.StreamsModule;
import de.melsicon.kafka.sensors.topology.TopologyModule;

/** Provides the bindings for our main application. */
@Module(
    includes = {
      ConfigurationModule.class,
      AppSerDesModule.class,
      StartUpModule.class,
      StreamsModule.class,
      TopologyModule.class
    })
/* package */ abstract class MainModule {
  private MainModule() {}
}
