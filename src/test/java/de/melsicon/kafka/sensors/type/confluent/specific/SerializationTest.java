package de.melsicon.kafka.sensors.type.confluent.specific;

import static com.google.common.truth.Truth.assertThat;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.State;
import de.melsicon.kafka.sensors.testutil.SchemaRegistryRule;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import java.time.Instant;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public final class SerializationTest {
  public static final String KAFKA_TOPIC = "topic";
  public static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  @ClassRule public static final SchemaRegistryRule registryTestResource = new SchemaRegistryRule();

  private static @MonotonicNonNull Serializer<SensorState> encoder;
  private static @MonotonicNonNull Deserializer<SensorState> decoder;

  @BeforeClass
  @EnsuresNonNull({"encoder", "decoder"})
  public static void before() {
    encoder = new SpecificAvroSerializer<>();
    encoder.configure(registryTestResource.configs(), /* isKey= */ false);

    decoder = new SpecificAvroDeserializer<>(SensorState.class);
    decoder.configure(registryTestResource.configs(), /* isKey= */ false);
  }

  @AfterClass
  @RequiresNonNull({"encoder", "decoder"})
  public static void after() {
    encoder.close();
    decoder.close();
  }

  private static SensorState createSensorState() {
    return SensorState.newBuilder().setId("7331").setTime(INSTANT).setState(State.ON).build();
  }

  @Test
  @RequiresNonNull({"encoder", "decoder"})
  public void canDecode() {
    var sensorState = createSensorState();

    var encoded = encoder.serialize(KAFKA_TOPIC, sensorState);

    // Check for “Magic Byte”
    // https://docs.confluent.io/current/schema-registry/serializer-formatter.html#wire-format
    assertThat(encoded[0]).isEqualTo((byte) 0);

    var decoded = decoder.deserialize(KAFKA_TOPIC, encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }
}
