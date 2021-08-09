package de.melsicon.kafka.sensors.serialization.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.errorprone.annotations.DoNotCall;
import de.melsicon.kafka.sensors.model.ImmutableSensorStateWithDuration;
import de.melsicon.kafka.sensors.model.SensorState;

@JsonDeserialize(builder = ImmutableSensorStateWithDuration.Builder.class)
public abstract class SensorStateWithDurationMixIn {
  private SensorStateWithDurationMixIn() {}

  @JsonUnwrapped
  public abstract SensorState getEvent();

  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @DoNotCall
    @JsonCreator
    public static ImmutableSensorStateWithDuration.Builder builder() {
      throw new UnsupportedOperationException();
    }

    @JsonUnwrapped
    public abstract ImmutableSensorStateWithDuration.Builder event(SensorState sensorState);
  }
}
