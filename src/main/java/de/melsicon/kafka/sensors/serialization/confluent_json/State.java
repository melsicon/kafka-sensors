package de.melsicon.kafka.sensors.serialization.confluent_json;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum State {
  @JsonProperty("off")
  OFF,
  @JsonProperty("on")
  ON
}
