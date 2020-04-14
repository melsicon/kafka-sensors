package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.melsicon.kafka.model.SensorState;
import edu.umd.cs.findbugs.annotations.Nullable;

@JsonDeserialize(builder = SensorState.Builder.class)
public abstract class SensorStateMixIn {
  private SensorStateMixIn() {}

  @JsonPOJOBuilder(withPrefix = "set")
  public abstract static class BuilderMixIn {
    private BuilderMixIn() {}

    @Nullable
    @JsonCreator
    public static SensorState.Builder newBuilder() {
      return null;
    }
  }
}
