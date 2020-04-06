package de.melsicon.kafka.serde.proto;

import static org.mapstruct.MappingConstants.ANY_REMAINING;
import static org.mapstruct.MappingConstants.NULL;

import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import de.melsicon.kafka.serde.mapping.protobuf.ProtoTypesMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

@Immutable
@Mapper(config = MapStructConfig.class, uses = ProtoTypesMapper.class)
public abstract class ProtoMapper {
  public static ProtoMapper instance() {
    return new ProtoMapperImpl();
  }

  public abstract SensorState map(de.melsicon.kafka.sensors.v1.SensorState sensorState);

  @InheritInverseConfiguration
  @ValueMapping(source = ANY_REMAINING, target = NULL)
  @ForOverride
  /* package */ abstract SensorState.State mapState(de.melsicon.kafka.sensors.v1.SensorState.State state);

  public abstract de.melsicon.kafka.sensors.v1.SensorState unmap(SensorState sensorState);

  @ValueMappings({
    @ValueMapping(source = "OFF", target = "STATE_OFF"),
    @ValueMapping(source = "ON", target = "STATE_ON"),
  })
  @ForOverride
  /* package */ abstract de.melsicon.kafka.sensors.v1.SensorState.State unmapState(
      SensorState.State state);

  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.v1.SensorStateWithDuration sensorState);

  public abstract de.melsicon.kafka.sensors.v1.SensorStateWithDuration unmap2(
      SensorStateWithDuration sensorState);
}
