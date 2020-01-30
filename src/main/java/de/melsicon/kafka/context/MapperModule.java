package de.melsicon.kafka.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.SensorStateWithDuration;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.avromapper.GenericMapper;
import de.melsicon.kafka.serde.avromapper.ReflectMapper;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import org.apache.avro.generic.GenericRecord;

@Module
/* package */ abstract class MapperModule {
  private MapperModule() {}

  @Provides
  /* package */ static AvroMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      specificMapper() {
    return SpecificMapper.instance();
  }

  @Provides
  /* package */ static AvroMapper<GenericRecord, GenericRecord> genericMapper() {
    return GenericMapper.instance();
  }

  @Provides
  /* package */ static AvroMapper<SensorState, SensorStateWithDuration> reflectMapper() {
    return ReflectMapper.instance();
  }
}
