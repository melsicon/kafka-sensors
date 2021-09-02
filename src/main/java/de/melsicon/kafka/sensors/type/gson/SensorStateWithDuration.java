package de.melsicon.kafka.sensors.type.gson;

import com.google.errorprone.annotations.Immutable;
import java.time.Duration;
import org.immutables.value.Value;

@Immutable
@Value.Style(passAnnotations = {Immutable.class})
@Value.Immutable
public abstract class SensorStateWithDuration
    implements SensorStateBase, WithSensorStateWithDuration {
  /* package */ SensorStateWithDuration() {}

  public static ImmutableSensorStateWithDuration.Builder builder() {
    return ImmutableSensorStateWithDuration.builder();
  }

  public abstract Duration getDuration();
}
