package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.melsicon.kafka.model.ImmutableSensorStateWithDuration;
import de.melsicon.kafka.model.SensorState;

@JsonDeserialize(builder = ImmutableSensorStateWithDuration.Builder.class)
public abstract class SensorStateWithDurationMixIn {
  private SensorStateWithDurationMixIn() {}

  @JsonUnwrapped
  public abstract SensorState getEvent();

  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @JsonCreator
    public static ImmutableSensorStateWithDuration.Builder builder() {
      throw new UnsupportedOperationException();
    }

    @JsonUnwrapped
    public abstract ImmutableSensorStateWithDuration.Builder event(SensorState sensorState);
  }
}
