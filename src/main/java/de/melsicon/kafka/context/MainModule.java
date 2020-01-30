package de.melsicon.kafka.context;

import dagger.Module;

@Module(
    includes = {
      ConfigurationModule.class,
      StartUpModule.class,
      StreamsModule.class,
      MapperModule.class
    })
/* package */ abstract class MainModule {
  private MainModule() {}
}
