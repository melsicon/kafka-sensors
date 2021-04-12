package de.melsicon.kafka.context;

import dagger.Module;
import de.melsicon.kafka.topology.TopologyModule;

/** Provides the bindings for our tests. */
@Module(includes = {TopologyModule.class})
/* package */ abstract class TestModule {
  private TestModule() {}
}
