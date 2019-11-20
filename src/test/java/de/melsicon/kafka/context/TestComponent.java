package de.melsicon.kafka.context;

import com.salesforce.kafka.test.AbstractKafkaTestResource;
import dagger.BindsInstance;
import dagger.Component;
import de.melsicon.kafka.topology.TopologyFactory;
import javax.inject.Singleton;

@Singleton
@Component(modules = {TestModule.class})
public abstract class TestComponent {
  /* package */ TestComponent() {}

  public static TestComponent.Factory factory() {
    return DaggerTestComponent.factory();
  }

  public abstract TopologyFactory myTopology();

  @Component.Factory
  public abstract static class Factory {
    public abstract TestComponent newTestComponent(
        @BindsInstance AbstractKafkaTestResource<?> kafkaTestResource);
  }
}
