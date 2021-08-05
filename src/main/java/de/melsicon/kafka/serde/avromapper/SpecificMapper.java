package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Immutable
@Mapper(config = MapStructConfig.class, uses = DurationMapper.class)
/* package */ abstract class SpecificMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.avro.SensorState,
        de.melsicon.kafka.sensors.avro.SensorStateWithDuration> {
  @Override
  public abstract @PolyNull SensorState map(
      de.melsicon.kafka.sensors.avro.@PolyNull SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.avro.@PolyNull SensorState unmap(
      @PolyNull SensorState sensorState);

  @Override
  public abstract @PolyNull SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.avro.@PolyNull SensorStateWithDuration sensorState);

  @Override
  @Mapping(ignore = true, target = "eventBuilder")
  public abstract de.melsicon.kafka.sensors.avro.@PolyNull SensorStateWithDuration unmap2(
      @PolyNull SensorStateWithDuration sensorState);
}
