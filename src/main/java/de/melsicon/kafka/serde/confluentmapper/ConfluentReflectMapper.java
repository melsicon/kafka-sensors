package de.melsicon.kafka.serde.confluentmapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class ConfluentReflectMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.confluent_reflect.SensorState,
        de.melsicon.kafka.sensors.confluent_reflect.SensorStateWithDuration> {
  public static ConfluentReflectMapper instance() {
    return new ConfluentReflectMapperImpl();
  }

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public abstract SensorState map(
      de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorState sensorState);

  @Override
  @AssertNonNullIfNonNull("sensorState")
  public abstract de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorState unmap(
      @Nullable SensorState sensorState);

  @Override
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorStateWithDuration sensorState);

  @Override
  @AssertNonNullIfNonNull("sensorState")
  public abstract de.melsicon.kafka.sensors.confluent_reflect.@Nullable SensorStateWithDuration
      unmap2(@Nullable SensorStateWithDuration sensorState);
}
