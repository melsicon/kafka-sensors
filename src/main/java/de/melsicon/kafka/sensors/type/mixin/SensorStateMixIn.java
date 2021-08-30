package de.melsicon.kafka.sensors.type.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.melsicon.kafka.sensors.model.ImmutableSensorState;

@JsonDeserialize(builder = ImmutableSensorState.Builder.class)
public abstract class SensorStateMixIn {
  private SensorStateMixIn() {}

  @JsonPOJOBuilder(withPrefix = "")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}
  }
}
