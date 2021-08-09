package de.melsicon.kafka.sensors.model;

import com.google.errorprone.annotations.Immutable;
import java.time.Instant;
import org.immutables.value.Value;

@Immutable
@Value.Style(passAnnotations = {Immutable.class})
@Value.Immutable
public abstract class SensorState implements WithSensorState {
  /* package */ SensorState() {}

  public static ImmutableSensorState.Builder builder() {
    return ImmutableSensorState.builder();
  }

  public abstract String getId();

  public abstract Instant getTime();

  public abstract State getState();

  public enum State {
    OFF("off"),
    ON("on");

    private final String value;

    State(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
