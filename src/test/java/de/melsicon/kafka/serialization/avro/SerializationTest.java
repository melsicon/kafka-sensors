package de.melsicon.kafka.serialization.avro;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.State;
import java.io.IOException;
import java.time.Instant;
import org.apache.avro.AvroMissingFieldException;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.message.MessageDecoder;
import org.apache.avro.message.MessageEncoder;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.BeforeClass;
import org.junit.Test;

public final class SerializationTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private static @MonotonicNonNull MessageEncoder<SensorState> encoder;
  private static @MonotonicNonNull MessageDecoder<SensorState> decoder;

  @BeforeClass
  @EnsuresNonNull({"encoder", "decoder"})
  public static void before() {
    encoder = SensorState.getEncoder();
    decoder = SensorState.getDecoder();
  }

  @Test
  @RequiresNonNull({"encoder", "decoder"})
  public void canDecode() throws IOException {
    var sensorState =
        SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(State.OFF).build();

    var encoded = encoder.encode(sensorState);

    // Check for single-record format marker
    // https://avro.apache.org/docs/current/spec.html#single_object_encoding
    assertThat(encoded.getShort(0)).isEqualTo((short) 0xc301);

    var decoded = decoder.decode(encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  public void stateIsRequired() {
    assertThrows(
        AvroMissingFieldException.class,
        () -> SensorState.newBuilder().setId("7331").setTime(INSTANT).build());
  }

  @Test
  @SuppressWarnings("nullness:argument")
  public void notNull() {
    assertThrows(
        AvroRuntimeException.class,
        () -> SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(null).build());
  }
}
