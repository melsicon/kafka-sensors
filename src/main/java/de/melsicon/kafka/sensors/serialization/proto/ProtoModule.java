package de.melsicon.kafka.sensors.serialization.proto;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorStateWithDuration;
import javax.inject.Singleton;

@Module
public abstract class ProtoModule {
  private ProtoModule() {}

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<SensorState, SensorStateWithDuration> protoMapper() {
    return new ProtoMapperImpl();
  }

  @Binds
  @IntoMap
  @StringKey(Name.PROTO)
  /* package */ abstract SensorStateSerdes protoSerdes(ProtoSerdes serdes);
}
