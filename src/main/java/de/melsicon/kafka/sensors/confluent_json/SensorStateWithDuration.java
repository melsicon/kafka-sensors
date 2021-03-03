package de.melsicon.kafka.sensors.confluent_json;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.Duration;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"NullAway", "nullness:initialization.field.uninitialized"})
public final class SensorStateWithDuration {
  @JsonUnwrapped public SensorState event;

  public Duration duration;

  @Override
  public int hashCode() {
    return Objects.hash(event, duration);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SensorStateWithDuration)) {
      return false;
    }
    var that = (SensorStateWithDuration) o;
    return Objects.equals(event, that.event) && Objects.equals(duration, that.duration);
  }
}
