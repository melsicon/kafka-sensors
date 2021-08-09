package de.melsicon.kafka.sensors.model;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import de.melsicon.kafka.sensors.model.SensorState.State;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;

public final class SensorStateTest {
  @Test
  public void creation() {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState = SensorState.builder().id("7331").time(instant).state(State.ON).build();

    assertThat(sensorState.getId()).isEqualTo("7331");
    assertThat(sensorState.getTime()).isEqualTo(instant);
    assertThat(sensorState.getState()).isEqualTo(State.ON);
  }

  @Test
  public void required() {
    assertThrows(IllegalStateException.class, () -> SensorState.builder().build());
  }

  @Test
  @SuppressWarnings("nullness:argument")
  public void notNull() {
    assertThrows(NullPointerException.class, () -> SensorState.builder().id(null).build());
  }

  @Test
  public void durationShouldNotBeNegative() {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState = SensorState.builder().id("7331").time(instant).state(State.ON).build();

    assertThrows(
        IllegalStateException.class,
        () ->
            SensorStateWithDuration.builder()
                .event(sensorState)
                .duration(Duration.ofMillis(-1))
                .build());
  }
}
