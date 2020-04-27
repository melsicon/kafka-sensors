package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Immutable
@Mapper(config = MapStructConfig.class, uses = DurationMapper.class)
public abstract class SpecificMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.avro.SensorState,
        de.melsicon.kafka.sensors.avro.SensorStateWithDuration> {
  public static SpecificMapper instance() {
    return new SpecificMapperImpl();
  }

  @Override
  @Nullable
  public abstract SensorState map(de.melsicon.kafka.sensors.avro.@Nullable SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.avro.@Nullable SensorState unmap(
      @Nullable SensorState sensorState);

  @Override
  @Nullable
  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.avro.@Nullable SensorStateWithDuration sensorState);

  @Override
  @Mapping(ignore = true, target = "eventBuilder")
  public abstract de.melsicon.kafka.sensors.avro.@Nullable SensorStateWithDuration unmap2(
      @Nullable SensorStateWithDuration sensorState);
}
