package de.melsicon.kafka.serde.avro;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import java.time.Duration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class SensorStateMapper {
  public static SensorStateMapper instance() {
    return new SensorStateMapperImpl();
  }

  public abstract SensorState map(de.melsicon.kafka.sensors.avro.SensorState sensorState);

  public abstract de.melsicon.kafka.sensors.avro.SensorState unmap(SensorState sensorState);

  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.avro.SensorStateWithDuration sensorState);

  @Mapping(ignore = true, target = "eventBuilder")
  public abstract de.melsicon.kafka.sensors.avro.SensorStateWithDuration unmap2(
      SensorStateWithDuration sensorState);

  protected Duration long2Duration(long millis) {
    return Duration.ofMillis(millis);
  }

  protected long duration2Long(Duration duration) {
    return duration.toMillis();
  }
}
