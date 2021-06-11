package de.melsicon.kafka.serde.proto;

import static org.mapstruct.MappingConstants.ANY_REMAINING;
import static org.mapstruct.MappingConstants.NULL;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import de.melsicon.kafka.serde.mapping.protobuf.ProtoTypesMapper;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Immutable
@Mapper(config = MapStructConfig.class, uses = ProtoTypesMapper.class)
@SuppressWarnings("UnnecessarilyFullyQualified")
public abstract class ProtoMapper {
  public static ProtoMapper instance() {
    return new ProtoMapperImpl();
  }

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

  @InheritInverseConfiguration
  @ValueMapping(source = ANY_REMAINING, target = NULL)
  /* package */ abstract SensorState.@PolyNull State mapState(
      de.melsicon.kafka.sensors.v1.SensorState.@PolyNull State state);

  public abstract de.melsicon.kafka.sensors.v1.@PolyNull SensorState unmap(
      @PolyNull SensorState sensorState);

  @ValueMappings({
    @ValueMapping(source = "OFF", target = "STATE_OFF"),
    @ValueMapping(source = "ON", target = "STATE_ON"),
  })
  /* package */ abstract de.melsicon.kafka.sensors.v1.SensorState.State unmapState(
      SensorState.State state);

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

  public abstract de.melsicon.kafka.sensors.v1.@PolyNull SensorStateWithDuration unmap2(
      @PolyNull SensorStateWithDuration sensorState);
}
