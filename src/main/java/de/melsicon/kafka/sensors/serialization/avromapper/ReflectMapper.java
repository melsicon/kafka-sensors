package de.melsicon.kafka.sensors.serialization.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import de.melsicon.kafka.sensors.type.avro.reflect.SensorState;
import de.melsicon.kafka.sensors.type.avro.reflect.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
/* package */ abstract class ReflectMapper
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
  public abstract @PolyNull SensorStateWithDuration unmap2(
      de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration sensorState);
}
