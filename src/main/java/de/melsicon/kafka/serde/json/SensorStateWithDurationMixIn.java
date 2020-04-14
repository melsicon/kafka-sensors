package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import edu.umd.cs.findbugs.annotations.Nullable;

@JsonDeserialize(builder = SensorStateWithDuration.Builder.class)
public abstract class SensorStateWithDurationMixIn {
  private SensorStateWithDurationMixIn() {}

  @JsonUnwrapped
  public abstract SensorState getEvent();

  @JsonPOJOBuilder(withPrefix = "set")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @Nullable
    @JsonCreator
    public static SensorStateWithDuration.Builder newBuilder() {
      return null;
    }

    @JsonUnwrapped
    public abstract SensorStateWithDuration.Builder setEvent(SensorState sensorState);
  }
}
