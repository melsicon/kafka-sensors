package de.melsicon.kafka.sensors.serde.context;

import dagger.Module;

@Module(includes = SerDesModule.class)
/* package */ abstract class TestModule {
  private TestModule() {}
}
