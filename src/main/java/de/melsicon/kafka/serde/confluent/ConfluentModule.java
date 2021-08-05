package de.melsicon.kafka.serde.confluent;

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
import de.melsicon.kafka.serde.confluentmapper.ConfluentMapperModule;
import javax.inject.Named;

@Module(includes = ConfluentMapperModule.class)
public abstract class ConfluentModule {
  private ConfluentModule() {}

  @Binds
  @IntoMap
  @StringKey(CONFLUENT_GENERIC)
  /* package */ abstract SensorStateSerdes genericSerdes(GenericSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(CONFLUENT_JSON)
  /* package */ abstract SensorStateSerdes jsonSerdes(JsonSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(CONFLUENT_PROTO)
  /* package */ abstract SensorStateSerdes protoSerdes(ProtoSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(CONFLUENT_REFLECT)
  /* package */ abstract SensorStateSerdes reflectSerdes(ReflectSerdes serdes);

  @Provides
  @IntoMap
  @StringKey(CONFLUENT_SPECIFIC)
  /* package */ static SensorStateSerdes specificSerdes(
      SpecificSerdesFactory factory,
      SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }

  @Provides
  @IntoMap
  @StringKey(CONFLUENT_DIRECT)
  /* package */ static SensorStateSerdes specificDirectSerdes(
      SpecificSerdesFactory factory,
      @Named("direct") SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    return factory.create(mapper);
  }
}
