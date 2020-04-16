package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class ConfluentReflectMapper
    implements AvroMapper<
        de.melsicon.kafka.sensors.confluent_reflect.SensorState,
        de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration> {
  public static ConfluentReflectMapper instance() {
    return new ConfluentReflectMapperImpl();
  }

  @Override
  @Nullable
  public abstract SensorState map(
      de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.confluent_reflect.SensorState unmap(
      @Nullable SensorState sensorState);

  @Override
  @Nullable
  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorStateWithDuration sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration unmap2(
      @Nullable SensorStateWithDuration sensorState);
}
