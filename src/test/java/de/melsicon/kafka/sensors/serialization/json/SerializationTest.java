package de.melsicon.kafka.sensors.serialization.json;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorState.State;
import java.io.IOException;
import java.time.Instant;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.Before;
import org.junit.Test;

public final class SerializationTest {
  private @MonotonicNonNull ObjectMapper mapper;

  @Before
  @EnsuresNonNull("mapper")
  public void before() {
    mapper = JsonHelper.mapper();
  }

  @Test
  @RequiresNonNull("mapper")
  public void canDecode() throws IOException {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState = SensorState.builder().id("7331").time(instant).state(State.ON).build();

    var encoded = mapper.writeValueAsBytes(sensorState);

    var decoded = mapper.readValue(encoded, SensorState.class);
    assertThat(decoded).isEqualTo(sensorState);
  }
}
