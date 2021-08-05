package de.melsicon.kafka.serde.context;

import dagger.Component;
import de.melsicon.kafka.serde.SensorStateSerdes;
import java.util.Map;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
@Component(modules = {TestModule.class})
public abstract class TestComponent {
  /* package */ TestComponent() {}

  public static TestComponent create() {
    return DaggerTestComponent.create();
  }

  public abstract Map<String, Provider<SensorStateSerdes>> sensorStateSerdesByName();
}
