package de.melsicon.kafka.serde;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.PolyNull;

public interface SensorStateMapper<S, T> {
  @PolyNull
  SensorState map(@PolyNull S sensorState);

  @PolyNull
  S unmap(@PolyNull SensorState sensorState);

  @PolyNull
  SensorStateWithDuration map2(@PolyNull T sensorState);

  @PolyNull
  T unmap2(@PolyNull SensorStateWithDuration sensorState);
}
