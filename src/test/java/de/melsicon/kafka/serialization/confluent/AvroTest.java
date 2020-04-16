package de.melsicon.kafka.serialization.confluent;

import static com.google.common.truth.Truth.assertThat;
import static de.melsicon.kafka.serialization.confluent.TestHelper.INSTANT;
import static de.melsicon.kafka.serialization.confluent.TestHelper.KAFKA_TOPIC;
import static de.melsicon.kafka.serialization.confluent.TestHelper.REGISTRY_SCOPE;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.State;
import de.melsicon.kafka.serde.confluent.SpecificAvroDeserializer;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import java.io.IOException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

@SuppressWarnings("nullness:initialization.static.fields.uninitialized") // Initialized in before
public final class AvroTest {
  @ClassRule
  public static final SchemaRegistryRule registryTestResource =
      new SchemaRegistryRule(REGISTRY_SCOPE);

  private static Serializer<SensorState> encoder;
  private static Deserializer<SensorState> decoder;

  @BeforeClass
  public static void before() {
    encoder = new SpecificAvroSerializer<>();
    decoder = new SpecificAvroDeserializer<>(SensorState.class);
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
    return SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(State.OFF).build();
  }

  @Test
  public void canDecode() throws IOException {
    var sensorState = createSensorState();

    var encoded = encoder.serialize(KAFKA_TOPIC, sensorState);

    // Check for “Magic Byte”
    // https://docs.confluent.io/current/schema-registry/serializer-formatter.html#wire-format
    assertThat(encoded[0]).isEqualTo((byte) 0);

    var decoded = decoder.deserialize(KAFKA_TOPIC, encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }
}
