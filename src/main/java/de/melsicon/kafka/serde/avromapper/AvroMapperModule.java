package de.melsicon.kafka.serde.avromapper;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.serde.SensorStateMapper;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.avro.generic.GenericRecord;

@Module
public abstract class AvroMapperModule {
  private AvroMapperModule() {}

  @Binds
  /* package */ abstract SensorStateMapper<GenericRecord, GenericRecord> genericMapper(
      GenericMapper mapper);

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<
          de.melsicon.kafka.sensors.reflect.SensorState,
          de.melsicon.kafka.sensors.reflect.SensorStateWithDuration>
      reflectMapper() {
    return new ReflectMapperImpl();
  }

  @Provides
  @Singleton
  @Named("direct")
  /* package */ static SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      specificDirectMapper() {
    return new SpecificDirectMapperImpl() {};
  }

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      specificMapper() {
    return new SpecificMapperImpl();
  }
}
