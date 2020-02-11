package de.melsicon.kafka.serialization.confluent;

import static de.melsicon.kafka.serialization.confluent.TestHelper.INSTANT;
import static de.melsicon.kafka.serialization.confluent.TestHelper.KAFKA_TOPIC;
import static de.melsicon.kafka.serialization.confluent.TestHelper.REGISTRY_SCOPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.State;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import java.io.IOException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public final class ReflectTest {
  @ClassRule
  public static final SchemaRegistryRule registryTestResource =
      new SchemaRegistryRule(REGISTRY_SCOPE);

  private static Serializer<SensorState> encoder;
  private static Deserializer<SensorState> decoder;

  @BeforeClass
  public static void before() {
    encoder = new ReflectionAvroSerializer<>();
    decoder = new ReflectionAvroDeserializer<>(SensorState.class);
    registerRegistry();
  }

  private static void registerRegistry() {
    registryTestResource.configureSerializer(encoder);
    registryTestResource.configureDeserializer(decoder);
  }

  @AfterClass
  public static void after() {
    encoder.close();
    decoder.close();
  }

  private static SensorState createSensorState() {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;
    sensorState.state = State.OFF;
    return sensorState;
  }

  @Test
  public void canDecode() throws IOException {
    var sensorState = createSensorState();

    var encoded = encoder.serialize(KAFKA_TOPIC, sensorState);

    // Check for “Magic Byte”
    // https://docs.confluent.io/current/schema-registry/serializer-formatter.html#wire-format
    assertThat(encoded).startsWith(0);

    var decoded = decoder.deserialize(KAFKA_TOPIC, encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  public void stateIsRequired() throws IOException {
    var sensorState = new SensorState();
    sensorState.id = "7331";
    sensorState.time = INSTANT;

    assertThatExceptionOfType(SerializationException.class)
        .isThrownBy(() -> encoder.serialize(KAFKA_TOPIC, sensorState));
  }
}
