package de.melsicon.kafka.sensors.benchmark;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorState.State;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import java.time.Duration;
import java.time.Instant;

public final class Constants {
  public static final String TOPIC = "topic";

  private Constants() {}

  public static SensorStateWithDuration createData() {
    var instant = Instant.ofEpochSecond(443634300L);

    var event = SensorState.builder().id("7331").time(instant).state(State.ON).build();

    return SensorStateWithDuration.builder().event(event).duration(Duration.ofSeconds(15)).build();
  }
}
