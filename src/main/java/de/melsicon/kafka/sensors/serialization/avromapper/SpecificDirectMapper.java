package de.melsicon.kafka.sensors.serialization.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Immutable
@Mapper(config = MapStructConfig.class, uses = DurationMapper.class)
/* package */ abstract class SpecificDirectMapper
    implements SensorStateMapper<SensorState, SensorStateWithDuration> {
  private static SensorState createSensorState() {
    return new SensorState();
  }

  private static SensorStateWithDuration createSensorStateWithDuration() {
    return new SensorStateWithDuration();
  }

  @Override
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorState map(
      @PolyNull SensorState sensorState);

  @Override
  public @PolyNull SensorState unmap(de.melsicon.kafka.sensors.model.@PolyNull SensorState from) {
    if (from == null) {
      return null;
    }
    var to = createSensorState();
    unmapto(from, to);
    return to;
  }

  @Override
  public abstract de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration map2(
      @PolyNull SensorStateWithDuration sensorState);

  @Override
  public @PolyNull SensorStateWithDuration unmap2(
      de.melsicon.kafka.sensors.model.@PolyNull SensorStateWithDuration from) {
    if (from == null) {
      return null;
    }
    var to = createSensorStateWithDuration();
    unmapto2(from, to);
    return to;
  }

  /* package */ abstract void unmapto(
      de.melsicon.kafka.sensors.model.SensorState from, @MappingTarget SensorState to);

  /* package */ abstract void unmapto2(
      de.melsicon.kafka.sensors.model.SensorStateWithDuration from,
      @MappingTarget SensorStateWithDuration to);
}
