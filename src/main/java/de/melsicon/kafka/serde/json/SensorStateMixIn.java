package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.melsicon.annotation.Nullable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import java.time.Instant;

@JsonDeserialize(builder = SensorState.Builder.class)
public abstract class SensorStateMixIn {
  private SensorStateMixIn() {}

  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @JsonCreator
    public static @Nullable SensorState.Builder builder() {
      return null;
    }

    @JsonProperty("id")
    public abstract SensorState.Builder setId(String id);

    @JsonProperty("time")
    public abstract SensorState.Builder setTime(Instant date1);

    @JsonProperty("state")
    public abstract SensorState.Builder setState(State state);
  }
}
