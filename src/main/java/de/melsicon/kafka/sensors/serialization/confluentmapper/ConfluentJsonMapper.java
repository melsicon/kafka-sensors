package de.melsicon.kafka.sensors.serialization.confluentmapper;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serialization.mapping.MapStructConfig;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.mapstruct.Mapper;

@Immutable
@Mapper(config = MapStructConfig.class)
/* package */ abstract class ConfluentJsonMapper
    implements SensorStateMapper<
        de.melsicon.kafka.sensors.serialization.confluent_json.SensorState,
        de.melsicon.kafka.sensors.serialization.confluent_json.SensorStateWithDuration> {
  @Override
  public abstract @PolyNull SensorState map(
      de.melsicon.kafka.sensors.serialization.confluent_json.@PolyNull SensorState sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.serialization.confluent_json.@PolyNull SensorState
      unmap(@PolyNull SensorState sensorState);

  @Override
  public abstract @PolyNull SensorStateWithDuration map2(
      de.melsicon.kafka.sensors.serialization.confluent_json.@PolyNull SensorStateWithDuration
          sensorState);

  @Override
  public abstract de.melsicon.kafka.sensors.serialization.confluent_json.@PolyNull
      SensorStateWithDuration unmap2(@PolyNull SensorStateWithDuration sensorState);
}
