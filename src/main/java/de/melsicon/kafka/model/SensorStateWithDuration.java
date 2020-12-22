package de.melsicon.kafka.model;

import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.time.Duration;

@Immutable
@AutoValue
public abstract class SensorStateWithDuration {
  /* package */ SensorStateWithDuration() {}

  public static Builder builder() {
    return new AutoValue_SensorStateWithDuration.Builder();
  }

  public abstract SensorState getEvent();

  public abstract Duration getDuration();

  @CanIgnoreReturnValue
  @AutoValue.Builder
  public abstract static class Builder {
    /**
     * Only for {@link com.fasterxml.jackson.annotation.JsonCreator}
     *
     * @deprecated Use {@link SensorState#builder}
     * @return A new Builder
     */
    @Deprecated
    public static final Builder newBuilder() {
      return SensorStateWithDuration.builder();
    }

    public abstract Builder setEvent(SensorState sensorState);

    public abstract Builder setDuration(Duration duration);

    /* package */ abstract SensorStateWithDuration autoBuild();

    @CheckReturnValue
    public final SensorStateWithDuration build() {
      var build = autoBuild();
      if (build.getDuration().isNegative()) {
        throw new IllegalStateException("Duration is negative");
      }
      return build;
    }
  }
}
