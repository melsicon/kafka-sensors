package de.melsicon.kafka.serialization.reflect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.State;
import java.io.IOException;
import java.time.Instant;
import org.apache.avro.AvroTypeException;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.MessageDecoder;
import org.apache.avro.message.MessageEncoder;
import org.apache.avro.reflect.ReflectData;
import org.junit.BeforeClass;
import org.junit.Test;

public final class SerializationTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private static MessageEncoder<SensorState> encoder;
  private static MessageDecoder<SensorState> decoder;

  @BeforeClass
  public static void before() {
    var model = ReflectData.get();
    var schema = model.getSchema(SensorState.class);
    encoder = new BinaryMessageEncoder<>(model, schema);
    decoder = new BinaryMessageDecoder<>(model, schema);
  }

  @Test
  public void canDecode() throws IOException {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;
    sensorState.state = State.OFF;

    var encoded = encoder.encode(sensorState);

    // Check for single-record format marker
    // http://avro.apache.org/docs/1.9.1/spec.html#single_object_encoding
    assertThat(encoded.getShort(0)).isEqualTo((short) 0xc301);

    var decoded = decoder.decode(encoded);

    assertThat(decoded).isEqualToComparingFieldByField(sensorState);
  }

  @Test
  public void stateIsRequired() throws IOException {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;

    assertThatExceptionOfType(AvroTypeException.class)
        .isThrownBy(() -> encoder.encode(sensorState));
  }
}
