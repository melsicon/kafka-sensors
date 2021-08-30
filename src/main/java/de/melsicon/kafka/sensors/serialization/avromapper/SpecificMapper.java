package de.melsicon.kafka.sensors.serialization.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Immutable
@Mapper(config = MapStructConfig.class, uses = DurationMapper.class)
/* package */ abstract class SpecificMapper
    implements SensorStateMapper<SensorState, SensorStateWithDuration> {
  @Override
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorState map(
      @PolyNull SensorState sensorState);

  @Override
  public abstract @PolyNull SensorState unmap(
      de.melsicon.kafka.sensors.model.@PolyNull SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration map2(
      @PolyNull SensorStateWithDuration sensorState);

  @Override
  @Mapping(ignore = true, target = "eventBuilder")
  public abstract @PolyNull SensorStateWithDuration unmap2(
      de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration sensorState);
}
