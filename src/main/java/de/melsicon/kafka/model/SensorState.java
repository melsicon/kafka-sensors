package de.melsicon.kafka.model;

import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.Immutable;
import java.time.Instant;

@Immutable
@AutoValue
public abstract class SensorState {
  /* package */ SensorState() {}

  public static Builder builder() {
    return new AutoValue_SensorState.Builder();
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

  @CanIgnoreReturnValue
  @AutoValue.Builder
  public abstract static class Builder {
    @DoNotCall // Only for @com.fasterxml.jackson.annotation.JsonCreator
    public static final Builder newBuilder() {
      return SensorState.builder();
    }

    public abstract Builder setId(String id);

    public abstract Builder setTime(Instant date1);

    public abstract Builder setState(State state);

    @CheckReturnValue
    public abstract SensorState build();
  }
}
