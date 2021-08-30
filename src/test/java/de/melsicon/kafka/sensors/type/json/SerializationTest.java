package de.melsicon.kafka.sensors.type.json;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = Instant.ofEpochSecond(443634300L);
    sensorState.state = SensorState.State.ON;

    var encoded = mapper.writeValueAsBytes(sensorState);
    var decoded = mapper.readValue(encoded, SensorState.class);

    assertThat(decoded).isEqualTo(sensorState);
  }
}
