package de.melsicon.kafka.serde;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface SensorStateMapper<S, T> {
  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  SensorState map(@Nullable S sensorState);

  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  S unmap(@Nullable SensorState sensorState);

  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  SensorStateWithDuration map2(@Nullable T sensorState);

  @AssertNonNullIfNonNull("sensorState")
  @Nullable
  T unmap2(@Nullable SensorStateWithDuration sensorState);
}
