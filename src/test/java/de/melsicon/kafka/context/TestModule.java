package de.melsicon.kafka.context;

import dagger.Module;

@Module(includes = {TestConfigurationModule.class})
/* package */ abstract class TestModule {
  private TestModule() {}
}
