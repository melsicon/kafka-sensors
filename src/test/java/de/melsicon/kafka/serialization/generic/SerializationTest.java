package de.melsicon.kafka.serialization.generic;

import static com.google.common.truth.Truth.assertThat;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.ENUM_OFF;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_ID;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_STATE;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.FIELD_TIME;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.MODEL;
import static de.melsicon.kafka.sensors.generic.SensorStateSchema.SCHEMA;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.time.Instant;
import org.apache.avro.AvroMissingFieldException;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.MessageDecoder;
import org.apache.avro.message.MessageEncoder;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.BeforeClass;
import org.junit.Test;

public final class SerializationTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private static @MonotonicNonNull MessageEncoder<GenericRecord> encoder;
  private static @MonotonicNonNull MessageDecoder<GenericRecord> decoder;

  @BeforeClass
  @EnsuresNonNull({"encoder", "decoder"})
  public static void before() {
    encoder = new BinaryMessageEncoder<>(MODEL, SCHEMA);
    decoder = new BinaryMessageDecoder<>(MODEL, SCHEMA);
  }

  @Test
  @RequiresNonNull({"encoder", "decoder"})
  public void canDecode() throws IOException {
    var sensorState =
        new GenericRecordBuilder(SCHEMA)
            .set(FIELD_ID, "7331")
            .set(FIELD_TIME, INSTANT)
            .set(FIELD_STATE, ENUM_OFF)
            .build();

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
        () ->
            new GenericRecordBuilder(SCHEMA)
                .set(FIELD_ID, "7331")
                .set(FIELD_TIME, INSTANT)
                .build());
  }

  @Test
  @SuppressWarnings("nullness:argument")
  public void notNull() {
    assertThrows(
        AvroRuntimeException.class,
        () ->
            new GenericRecordBuilder(SCHEMA)
                .set(FIELD_ID, "7331")
                .set(FIELD_TIME, INSTANT)
                .set(FIELD_STATE, null)
                .build());
  }
}
