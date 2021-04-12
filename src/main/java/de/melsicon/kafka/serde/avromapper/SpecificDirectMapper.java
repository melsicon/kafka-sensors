package de.melsicon.kafka.serde.avromapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Immutable
@Mapper(config = MapStructConfig.class, uses = DurationMapper.class)
public abstract class SpecificDirectMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.avro.SensorState,
        de.melsicon.kafka.sensors.avro.SensorStateWithDuration> {

  public static SpecificDirectMapper instance() {
    return new SpecificDirectMapperImpl();
  }

  private static de.melsicon.kafka.sensors.avro.SensorState createSensorState() {
    return new de.melsicon.kafka.sensors.avro.SensorState();
  }

  private static de.melsicon.kafka.sensors.avro.SensorStateWithDuration
      createSensorStateWithDuration() {
    return new de.melsicon.kafka.sensors.avro.SensorStateWithDuration();
  }

  @Override
  public abstract @PolyNull SensorState map(
      de.melsicon.kafka.sensors.avro.@PolyNull SensorState sensorState);

  @Override
  public de.melsicon.kafka.sensors.avro.@PolyNull SensorState unmap(@PolyNull SensorState from) {
    if (from == null) {
      return null;
    }
    var to = createSensorState();
    unmapto(from, to);
    return to;
  }

  @Override
  public abstract @PolyNull SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.avro.@PolyNull SensorStateWithDuration sensorState);

  @Override
  public de.melsicon.kafka.sensors.avro.@PolyNull SensorStateWithDuration unmap2(
      @PolyNull SensorStateWithDuration from) {
    if (from == null) {
      return null;
    }
    var to = createSensorStateWithDuration();
    unmapto2(from, to);
    return to;
  }

  /* package */ abstract void unmapto(
      de.melsicon.kafka.model.SensorState from,
      @MappingTarget de.melsicon.kafka.sensors.avro.SensorState to);

  /* package */ abstract void unmapto2(
      de.melsicon.kafka.model.SensorStateWithDuration from,
      @MappingTarget de.melsicon.kafka.sensors.avro.SensorStateWithDuration to);
}
