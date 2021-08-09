package de.melsicon.kafka.sensors.serialization.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.errorprone.annotations.DoNotCall;
import de.melsicon.kafka.sensors.model.ImmutableSensorState;

@JsonDeserialize(builder = ImmutableSensorState.Builder.class)
public abstract class SensorStateMixIn {
  private SensorStateMixIn() {}

  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @DoNotCall
    @JsonCreator
    public static ImmutableSensorState.Builder builder() {
      throw new UnsupportedOperationException();
    }
  }
}
