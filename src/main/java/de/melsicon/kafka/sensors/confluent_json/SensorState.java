package de.melsicon.kafka.sensors.confluent_json;

import java.time.Instant;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"NullAway", "nullness:initialization.fields.uninitialized"})
public final class SensorState {
  public String id;

  public Instant time;

  public State state;

  @Override
  public int hashCode() {
    return Objects.hash(id, time, state);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SensorState)) {
      return false;
    }
    var that = (SensorState) o;
    return Objects.equals(id, that.id) && Objects.equals(time, that.time) && state == that.state;
  }
}
