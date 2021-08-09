package de.melsicon.kafka.sensors.topology.context;

import dagger.Component;
import de.melsicon.kafka.sensors.serde.NamedSerDes;
import java.util.List;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ParameterModule.class})
public abstract class ParameterComponent {
  /* package */ ParameterComponent() {}

  public static ParameterComponent create() {
    return DaggerParameterComponent.create();
  }

  public abstract List<NamedSerDes> serdes();
}
