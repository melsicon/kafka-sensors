package de.melsicon.kafka.serde.confluentmapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
public abstract class ConfluentJsonMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.confluent_json.SensorState,
        de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration> {
  public static ConfluentJsonMapper instance() {
    return new ConfluentJsonMapperImpl();
  }

  @Override
  @Nullable
  public abstract SensorState map(
      de.melsicon.kafka.sensors.confluent_json.@Nullable SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.confluent_json.SensorState unmap(
      @Nullable SensorState sensorState);

  @Override
  @Nullable
  public abstract SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.confluent_json.@Nullable SensorStateWithDuration sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration unmap2(
      @Nullable SensorStateWithDuration sensorState);
}
