package de.melsicon.kafka.sensors.model;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.time.Duration;
import org.immutables.value.Value;

@Immutable
@Value.Style(passAnnotations = {Immutable.class})
@Value.Immutable
public abstract class SensorStateWithDuration implements WithSensorStateWithDuration {
  /* package */ SensorStateWithDuration() {}

  public static ImmutableSensorStateWithDuration.Builder builder() {
    return ImmutableSensorStateWithDuration.builder();
  }

  public abstract SensorState getEvent();

  public abstract Duration getDuration();

  @Value.Check
  /* package */ void check() {
    var duration = getDuration();
    Preconditions.checkState(!duration.isNegative(), "Duration is negative");
  }
}
