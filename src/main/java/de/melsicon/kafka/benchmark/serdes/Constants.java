package de.melsicon.kafka.benchmark.serdes;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import java.time.Duration;
import java.time.Instant;

public final class Constants {
  public static final String TOPIC = "topic";

  private Constants() {}

  public static SensorStateWithDuration createData() {
    var instant = Instant.ofEpochSecond(443634300L);

    var event = SensorState.builder().id("7331").time(instant).state(SensorState.State.OFF).build();

    return SensorStateWithDuration.builder().event(event).duration(Duration.ofSeconds(15)).build();
  }
}
