package de.melsicon.kafka.serialization.avro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.State;
import java.io.IOException;
import java.time.Instant;
import org.apache.avro.AvroMissingFieldException;
import org.apache.avro.AvroRuntimeException;
import org.junit.Test;

public final class SerializationTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  @Test
  public void canDecode() throws IOException {
    var sensorState =
        SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(State.OFF).build();

    var encoded = SensorState.getEncoder().encode(sensorState);
    var decoded = SensorState.getDecoder().decode(encoded);

    assertThat(decoded).isEqualToComparingFieldByField(sensorState);
  }

  @Test
  public void stateIsRequired() {
    assertThatExceptionOfType(AvroMissingFieldException.class)
        .isThrownBy(() -> SensorState.newBuilder().setId("7331").setTime(INSTANT).build());
  }

  @Test
  @SuppressWarnings("NullAway")
  public void notNull() {
    assertThatExceptionOfType(AvroRuntimeException.class)
        .isThrownBy(
            () -> SensorState.newBuilder().setId(null).setTime(INSTANT).setState(null).build());
  }
}
