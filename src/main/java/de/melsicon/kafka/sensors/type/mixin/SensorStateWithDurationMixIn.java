package de.melsicon.kafka.sensors.type.mixin;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
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

    @JsonUnwrapped
    public abstract ImmutableSensorStateWithDuration.Builder event(SensorState sensorState);
  }
}
