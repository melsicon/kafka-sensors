package de.melsicon.kafka.sensors.type.gson;

import static com.google.common.truth.Truth.assertThat;

import com.google.gson.Gson;
import java.time.Instant;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.Before;
import org.junit.Test;

public final class SerializationTest {
  private @MonotonicNonNull Gson gson;

  @Before
  @EnsuresNonNull("gson")
  public void before() {
    gson = GsonHelper.gson();
  }

  @Test
  @RequiresNonNull("gson")
  public void canDecode() {
    var instant = Instant.ofEpochSecond(443634300L);

    var sensorState =
        SensorState.builder().id("7331").time(instant).state(SensorState.State.ON).build();

    var encoded = gson.toJson(sensorState, SensorState.class);

    var decoded = gson.fromJson(encoded, SensorState.class);
    assertThat(decoded).isEqualTo(sensorState);
  }
}
