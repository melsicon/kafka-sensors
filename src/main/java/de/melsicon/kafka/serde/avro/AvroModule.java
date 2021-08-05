package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.Name.*;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapperModule;
import javax.inject.Named;

@Module(includes = AvroMapperModule.class)
public abstract class AvroModule {
  private AvroModule() {}

  @Binds
  @IntoMap
  @StringKey(AVRO_GENERIC)
  /* package */ abstract SensorStateSerdes genericSerdes(GenericSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(AVRO_REFLECT)
  /* package */ abstract SensorStateSerdes reflectSerdes(ReflectSerdes serdes);

  @Provides
  @IntoMap
  @StringKey(AVRO_SPECIFIC)
  /* package */ static SensorStateSerdes specificSerdes(
      SpecificSerdesFactory factory,
      SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Provides
  @IntoMap
  @StringKey(AVRO_DIRECT)
  /* package */ static SensorStateSerdes specificDirectSerdes(
      SpecificSerdesFactory factory,
      @Named("direct") SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }
}
