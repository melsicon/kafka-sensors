package de.melsicon.kafka.sensors.serde;

import com.google.errorprone.annotations.Immutable;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import org.checkerframework.checker.nullness.qual.PolyNull;

@Immutable
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
