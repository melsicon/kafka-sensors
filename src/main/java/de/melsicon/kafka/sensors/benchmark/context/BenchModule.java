package de.melsicon.kafka.sensors.benchmark.context;

import dagger.Module;
import de.melsicon.kafka.sensors.serde.context.SerDesModule;

@Module(subcomponents = IterationComponent.class, includes = SerDesModule.class)
/* package */ abstract class BenchModule {
  private BenchModule() {}
}
