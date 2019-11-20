package de.melsicon.kafka.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import io.helidon.config.Config;
import javax.inject.Singleton;

@Module
/* package */ abstract class ConfigurationModule {
  private ConfigurationModule() {}

  @Provides
  @Singleton
  /* package */ static KafkaConfiguration kafkaConfiguration(Config config) {
    return KafkaConfiguration.extract(config);
  }
}
