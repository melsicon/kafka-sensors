package de.melsicon.kafka.serde;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface SensorStateMapper<S, T> {
  @Nullable
  SensorState map(@Nullable S sensorState);

  @Nullable
  S unmap(@Nullable SensorState sensorState);

  @Nullable
  SensorStateWithDuration map2(@Nullable T sensorState);

  @Nullable
  T unmap2(@Nullable SensorStateWithDuration sensorState);
}
