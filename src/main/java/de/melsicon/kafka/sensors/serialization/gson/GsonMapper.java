package de.melsicon.kafka.sensors.serialization.gson;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import de.melsicon.kafka.sensors.type.gson.SensorState;
import de.melsicon.kafka.sensors.type.gson.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Immutable
@Mapper(config = MapStructConfig.class)
/* package */ abstract class GsonMapper
    implements SensorStateMapper<SensorState, SensorStateWithDuration> {
  @Override
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorState map(
      @PolyNull SensorState sensorState);

  @Override
  public abstract @PolyNull SensorState unmap(
      de.melsicon.kafka.sensors.model.@PolyNull SensorState sensorState);

  @Override
  @Mapping(source = "id", target = "event.id")
  @Mapping(source = "time", target = "event.time")
  @Mapping(source = "state", target = "event.state")
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration map2(
      @PolyNull SensorStateWithDuration sensorState);

  @Override
  @Mapping(source = "event.id", target = "id")
  @Mapping(source = "event.time", target = "time")
  @Mapping(source = "event.state", target = "state")
  public abstract @PolyNull SensorStateWithDuration unmap2(
      de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration sensorState);
}
