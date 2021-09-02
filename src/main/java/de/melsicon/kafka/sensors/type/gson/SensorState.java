package de.melsicon.kafka.sensors.type.gson;

import com.google.errorprone.annotations.Immutable;
import org.immutables.value.Value;

@Immutable
@Value.Style(passAnnotations = {Immutable.class})
@Value.Immutable
public abstract class SensorState implements SensorStateBase, WithSensorState {
  /* package */ SensorState() {}

  public static ImmutableSensorState.Builder builder() {
    return ImmutableSensorState.builder();
  }
}
