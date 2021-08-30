package de.melsicon.kafka.sensors.serialization.confluentmapper;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.type.confluent.reflect.SensorState;
import de.melsicon.kafka.sensors.type.confluent.reflect.SensorStateWithDuration;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.avro.generic.GenericRecord;

@Module
public abstract class ConfluentMapperModule {
  private ConfluentMapperModule() {}

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<SensorState, SensorStateWithDuration>
      confluentReflectMapper() {
    return new ConfluentReflectMapperImpl();
  }

  @Binds
  @Named("confluent")
  /* package */ abstract SensorStateMapper<GenericRecord, GenericRecord> confluentGenericMapper(
      ConfluentGenericMapper mapper);
}
