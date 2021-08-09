package de.melsicon.kafka.sensors.serialization.avro;

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
public abstract class AvroModule {
  private AvroModule() {}

  @Provides
  @IntoMap
  @StringKey(Name.AVRO_SPECIFIC)
  /* package */ static SensorStateSerdes specificSerdes(
      SpecificSerdesFactory factory,
      SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Provides
  @IntoMap
  @StringKey(Name.AVRO_DIRECT)
  /* package */ static SensorStateSerdes specificDirectSerdes(
      SpecificSerdesFactory factory,
      @Named("direct") SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Binds
  @IntoMap
  @StringKey(Name.AVRO_GENERIC)
  /* package */ abstract SensorStateSerdes genericSerdes(GenericSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.AVRO_REFLECT)
  /* package */ abstract SensorStateSerdes reflectSerdes(ReflectSerdes serdes);
}
