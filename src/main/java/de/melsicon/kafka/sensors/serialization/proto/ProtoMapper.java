package de.melsicon.kafka.sensors.serialization.proto;

import static org.mapstruct.MappingConstants.ANY_REMAINING;
import static org.mapstruct.MappingConstants.NULL;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import de.melsicon.kafka.sensors.serialization.mapping.protobuf.ProtoTypesMapper;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Immutable
@Mapper(config = MapStructConfig.class, uses = ProtoTypesMapper.class)
@SuppressWarnings("UnnecessarilyFullyQualified")
/* package */ abstract class ProtoMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.v1.SensorState,
        de.melsicon.kafka.sensors.v1.SensorStateWithDuration> {

  @Override
  public final @PolyNull SensorState map(
      de.melsicon.kafka.sensors.v1.@PolyNull SensorState sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorState.builder()
        .id(sensorState.getId())
        .state(mapState(sensorState.getState()))
        .time(ProtoTypesMapper.timestamp2Instant(sensorState.getTime()))
        .build();
  }

  @Override
  public abstract de.melsicon.kafka.sensors.v1.@PolyNull SensorState unmap(
      @PolyNull SensorState sensorState);

  @Override
  public final @PolyNull SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.v1.@PolyNull SensorStateWithDuration sensorState) {
    if (sensorState == null) {
      return null;
    }
    return SensorStateWithDuration.builder()
        .event(map(sensorState.getEvent()))
        .duration(ProtoTypesMapper.duration2Duration(sensorState.getDuration()))
        .build();
  }

  @Override
  public abstract de.melsicon.kafka.sensors.v1.@PolyNull SensorStateWithDuration unmap2(
      @PolyNull SensorStateWithDuration sensorState);

  @InheritInverseConfiguration
  @ValueMapping(source = ANY_REMAINING, target = NULL)
  /* package */ abstract SensorState.@PolyNull State mapState(
      de.melsicon.kafka.sensors.v1.SensorState.@PolyNull State state);

  @ValueMappings({
    @ValueMapping(source = "OFF", target = "STATE_OFF"),
    @ValueMapping(source = "ON", target = "STATE_ON"),
  })
  /* package */ abstract de.melsicon.kafka.sensors.v1.SensorState.State unmapState(
      SensorState.State state);
}
