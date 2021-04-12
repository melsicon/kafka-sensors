package de.melsicon.kafka.context;

import dagger.BindsInstance;
import dagger.Component;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import javax.inject.Singleton;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.Topology;

@Singleton
@Component(modules = {TestModule.class})
public abstract class TestComponent {
  /* package */ TestComponent() {}

  public static Factory factory() {
    return DaggerTestComponent.factory();
  }

  public abstract Topology topology();

  @Component.Factory
  public abstract static class Factory {
    public abstract TestComponent newTestComponent(
        @BindsInstance KafkaConfiguration configuration,
        @BindsInstance @InputSerde Serde<SensorState> inputSerde,
        @BindsInstance @StoreSerde Serde<SensorState> storeSerde,
        @BindsInstance @ResultSerde Serde<SensorStateWithDuration> resultSerde);
  }
}
