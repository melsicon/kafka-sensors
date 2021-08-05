package de.melsicon.kafka.benchmark.serdes;

import dagger.Component;
import de.melsicon.kafka.serde.SensorStateSerdes;
import java.util.Map;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
@Component(modules = {BenchModule.class})
public abstract class BenchComponent {
  /* package */ BenchComponent() {}

  public static BenchComponent create() {
    return DaggerBenchComponent.create();
  }

  public abstract Map<String, Provider<SensorStateSerdes>> sensorStateSerdesByName();

  public abstract IterationComponent.Factory iterationComponentFactory();
}
