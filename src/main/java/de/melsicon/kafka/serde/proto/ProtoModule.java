package de.melsicon.kafka.serde.proto;

import static de.melsicon.kafka.serde.Name.*;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
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
  @StringKey(PROTO)
  /* package */ abstract SensorStateSerdes protoSerdes(ProtoSerdes serdes);
}
