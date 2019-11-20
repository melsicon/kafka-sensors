package de.melsicon.kafka.context;

import com.salesforce.kafka.test.AbstractKafkaTestResource;
import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import javax.inject.Singleton;

@Module
/* package */ abstract class TestConfigurationModule {
  private TestConfigurationModule() {}

  @Singleton
  @Provides
  /* package */ static KafkaConfiguration kafkaConfiguration(
      AbstractKafkaTestResource<?> kafkaTestResource) {
    return KafkaConfiguration.builder().build();
  }
}
