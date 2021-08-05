package de.melsicon.kafka.benchmark.serdes;

import dagger.BindsInstance;
import dagger.Subcomponent;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

@Subcomponent(modules = IterationModule.class)
public abstract class IterationComponent {
  /* package */ IterationComponent() {}

  public abstract Serializer<SensorStateWithDuration> serializer();

  public abstract Deserializer<SensorStateWithDuration> deserializer();

  @Subcomponent.Factory
  public abstract static class Factory {
    public abstract IterationComponent newIterationComponent(
        @BindsInstance String serdes, @BindsInstance Map<String, String> serdeConfig);
  }
}
