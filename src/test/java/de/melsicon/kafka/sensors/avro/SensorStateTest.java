package de.melsicon.kafka.sensors.avro;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import org.apache.avro.AvroMissingFieldException;
import org.apache.avro.AvroRuntimeException;
import org.junit.Test;

public final class SensorStateTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private static SensorState decode(ByteBuffer encoded) {
    var decoder = SensorState.getDecoder();

    try {
      return decoder.decode(encoded);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static ByteBuffer encode(SensorState sensorState) {
    var encoder = SensorState.getEncoder();

    try {
      return encoder.encode(sensorState);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Test
  public void canDecode() {
    var sensorState =
        SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(State.OFF).build();

    var encoded = encode(sensorState);
    var decoded = decode(encoded);

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
