package de.melsicon.kafka.topology.context;

import dagger.BindsInstance;
import dagger.Component;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.topology.TopologyModule;
import javax.inject.Singleton;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.Topology;

@Singleton
@Component(modules = {TopologyModule.class})
public abstract class TopologyComponent {
  /* package */ TopologyComponent() {}

  public static Builder builder() {
    return DaggerTopologyComponent.builder();
  }

  public abstract Topology topology();

  @Component.Builder
  public interface Builder {
    @BindsInstance
    Builder configuration(KafkaConfiguration configuration);

    @BindsInstance
    Builder inputSerde(@InputSerde Serde<SensorState> serde);

    @BindsInstance
    Builder storeSerde(@StoreSerde Serde<SensorState> serde);

    @BindsInstance
    Builder resultSerde(@ResultSerde Serde<SensorStateWithDuration> serde);

    TopologyComponent build();
  }
}
