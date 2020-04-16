package de.melsicon.kafka.serde.avromapper;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface AvroMapper<S, T> {
  @Nullable
  SensorState map(@Nullable S sensorState);

  @Nullable
  S unmap(@Nullable SensorState sensorState);

  @Nullable
  SensorStateWithDuration map2(@Nullable T sensorState);

  @Nullable
  T unmap2(@Nullable SensorStateWithDuration sensorState);
}
