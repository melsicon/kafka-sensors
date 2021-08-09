package de.melsicon.kafka.sensors.serialization.confluent;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import javax.inject.Named;

@Module
public abstract class ConfluentModule {
  private ConfluentModule() {}

  @Provides
  @IntoMap
  @StringKey(Name.CONFLUENT_SPECIFIC)
  /* package */ static SensorStateSerdes specificSerdes(
      SpecificSerdesFactory factory,
      SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Provides
  @IntoMap
  @StringKey(Name.CONFLUENT_DIRECT)
  /* package */ static SensorStateSerdes specificDirectSerdes(
      SpecificSerdesFactory factory,
      @Named("direct") SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Binds
  @IntoMap
  @StringKey(Name.CONFLUENT_GENERIC)
  /* package */ abstract SensorStateSerdes genericSerdes(GenericSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.CONFLUENT_JSON)
  /* package */ abstract SensorStateSerdes jsonSerdes(JsonSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.CONFLUENT_PROTO)
  /* package */ abstract SensorStateSerdes protoSerdes(ProtoSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.CONFLUENT_REFLECT)
  /* package */ abstract SensorStateSerdes reflectSerdes(ReflectSerdes serdes);
}
