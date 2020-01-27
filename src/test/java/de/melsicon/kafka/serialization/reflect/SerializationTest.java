package de.melsicon.kafka.serialization.reflect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.melsicon.kafka.serde.reflect.SensorState;
import de.melsicon.kafka.serde.reflect.SensorState.State;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import org.apache.avro.AvroTypeException;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.junit.BeforeClass;
import org.junit.Test;

public final class SerializationTest {
  private static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private static DatumWriter<SensorState> writer;
  private static DatumReader<SensorState> reader;

  @BeforeClass
  public static void before() {
    var data = ReflectData.get();
    var schema = data.getSchema(SensorState.class);

    writer = new ReflectDatumWriter<>(schema, data);
    reader = new ReflectDatumReader<>(schema, schema, data);
  }

  private static SensorState readSensorState(byte[] bytes) throws IOException {
    var decoderFactory = DecoderFactory.get();
    var decoder = decoderFactory.binaryDecoder(bytes, null);
    return reader.read(null, decoder);
  }

  private static void writeSensorState(SensorState sensorState, ByteArrayOutputStream out)
      throws IOException {
    var encoderFactory = EncoderFactory.get();
    var encoder = encoderFactory.directBinaryEncoder(out, null);
    writer.write(sensorState, encoder);
    encoder.flush();
  }

  @Test
  public void canDecode() throws IOException {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;
    sensorState.state = State.OFF;

    var out = new ByteArrayOutputStream();
    writeSensorState(sensorState, out);

    var encoded = out.toByteArray();
    var decoded = readSensorState(encoded);

    assertThat(decoded).isEqualToComparingFieldByField(sensorState);
  }

  @Test
  public void stateIsRequired() {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;

    var out = new ByteArrayOutputStream();

    var encoderFactory = EncoderFactory.get();
    var encoder = encoderFactory.directBinaryEncoder(out, null);

    assertThatExceptionOfType(AvroTypeException.class)
        .isThrownBy(() -> writer.write(sensorState, encoder));
  }
}
