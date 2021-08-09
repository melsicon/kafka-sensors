package de.melsicon.kafka.sensors.serialization.confluent;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState;
import de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState.State;
import de.melsicon.kafka.sensors.testutil.SchemaRegistryRule;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public final class ReflectTest {
  @ClassRule
  public static final SchemaRegistryRule registryTestResource =
      new SchemaRegistryRule(TestHelper.REGISTRY_SCOPE);

  private static @MonotonicNonNull Serializer<SensorState> encoder;
  private static @MonotonicNonNull Deserializer<SensorState> decoder;

  @BeforeClass
  @EnsuresNonNull({"encoder", "decoder"})
  public static void before() {
    encoder = new ReflectionAvroSerializer<>();
    decoder = new ReflectionAvroDeserializer<>(SensorState.class);
    registerRegistry();
  }

  @RequiresNonNull({"encoder", "decoder"})
  private static void registerRegistry() {
    registryTestResource.configureSerializer(encoder);
    registryTestResource.configureDeserializer(decoder);
  }

  @AfterClass
  @RequiresNonNull({"encoder", "decoder"})
  public static void after() {
    encoder.close();
    decoder.close();
  }

  private static SensorState createSensorState() {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = TestHelper.INSTANT;
    sensorState.state = State.ON;
    return sensorState;
  }

  @Test
  @RequiresNonNull({"encoder", "decoder"})
  public void canDecode() {
    var sensorState = createSensorState();

    var encoded = encoder.serialize(TestHelper.KAFKA_TOPIC, sensorState);

    // Check for “Magic Byte”
    // https://docs.confluent.io/current/schema-registry/serializer-formatter.html#wire-format
    assertThat(encoded[0]).isEqualTo((byte) 0);

    var decoded = decoder.deserialize(TestHelper.KAFKA_TOPIC, encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  @RequiresNonNull("encoder")
  public void stateIsRequired() {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = TestHelper.INSTANT;

    var enc = encoder; // https://github.com/typetools/checker-framework/issues/1248
    assertThrows(
        SerializationException.class, () -> enc.serialize(TestHelper.KAFKA_TOPIC, sensorState));
  }
}
