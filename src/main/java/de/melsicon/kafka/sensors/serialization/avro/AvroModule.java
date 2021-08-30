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
import de.melsicon.kafka.sensors.type.avro.generic.GenericModule;
import de.melsicon.kafka.sensors.type.avro.reflect.ReflectModule;
import de.melsicon.kafka.sensors.type.avro.specific.SpecificModule;
import java.util.Set;
import javax.inject.Named;
import org.apache.avro.Schema;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.message.SchemaStore.Cache;

@Module(includes = {SpecificModule.class, GenericModule.class, ReflectModule.class})
public abstract class AvroModule {
  private AvroModule() {}

  @Provides
  /* package */ static SchemaStore schemaStore(Set<Schema> schemata) {
    var resolver = new Cache();
    schemata.forEach(resolver::addSchema);
    return resolver;
  }

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
