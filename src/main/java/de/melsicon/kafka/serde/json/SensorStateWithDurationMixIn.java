package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.melsicon.annotation.Nullable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;

@JsonDeserialize(builder = SensorStateWithDuration.Builder.class)
public abstract class SensorStateWithDurationMixIn {
  private SensorStateWithDurationMixIn() {}

  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @JsonCreator
    public static @Nullable SensorStateWithDuration.Builder builder() {
      return null;
    }

    @JsonProperty("event")
    public abstract SensorStateWithDuration.Builder setEvent(SensorState sensorState);

    @JsonProperty("duration")
    public abstract SensorStateWithDuration.Builder setDuration(Duration duration);
  }
}
