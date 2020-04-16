package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;

@JsonDeserialize(builder = SensorStateWithDuration.Builder.class)
public abstract class SensorStateWithDurationMixIn {
  private SensorStateWithDurationMixIn() {}

  @JsonUnwrapped
  public abstract SensorState getEvent();

  @JsonPOJOBuilder(withPrefix = "set")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @JsonCreator
    public static SensorStateWithDuration.Builder newBuilder() {
      throw new UnsupportedOperationException();
    }

    @JsonUnwrapped
    public abstract SensorStateWithDuration.Builder setEvent(SensorState sensorState);
  }
}
