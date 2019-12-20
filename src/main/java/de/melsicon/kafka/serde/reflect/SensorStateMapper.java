package de.melsicon.kafka.serde.reflect;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class SensorStateMapper {
  public static SensorStateMapper instance() {
    return new SensorStateMapperImpl();
  }

  public abstract de.melsicon.kafka.model.SensorState map(SensorState sensorState);

  public abstract SensorState unmap(de.melsicon.kafka.model.SensorState sensorState);

  public abstract de.melsicon.kafka.model.SensorStateWithDuration map2(
      SensorStateWithDuration sensorState);

  public abstract SensorStateWithDuration unmap2(
      de.melsicon.kafka.model.SensorStateWithDuration sensorState);
}
