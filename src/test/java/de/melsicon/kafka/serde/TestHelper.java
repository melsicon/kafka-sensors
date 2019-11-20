package de.melsicon.kafka.serde;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import java.time.Instant;

/* package */ final class TestHelper {
  /* package */ static final String REGISTRY_SCOPE = "test";
  /* package */ static final String KAFKA_TOPIC = "topic";

  private TestHelper() {}

  /* package */
  static SensorState standardSensorState() {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();
  }
}
