package de.melsicon.kafka.sensors.type.confluent.proto;

import static com.google.common.truth.Truth.assertThat;

import com.google.protobuf.Timestamp;
import de.melsicon.kafka.sensors.testutil.SchemaRegistryRule;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorState.State;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
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
    encoder = new KafkaProtobufSerializer<>();
    encoder.configure(registryTestResource.configs(), /* isKey= */ false);

    decoder = new KafkaProtobufDeserializer<>(SensorState.class);
    decoder.configure(registryTestResource.configs(), /* isKey= */ false);
  }

  @AfterClass
  @RequiresNonNull({"encoder", "decoder"})
  public static void after() {
    encoder.close();
    decoder.close();
  }

  @Test
  @RequiresNonNull({"encoder", "decoder"})
  public void canDecode() {
    var instant = Instant.ofEpochSecond(443634300L);
    var time =
        Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();

    var sensorState =
        SensorState.newBuilder().setId("7331").setTime(time).setState(State.STATE_OFF).build();

    var encoded = encoder.serialize(KAFKA_TOPIC, sensorState);

    // Check for “Magic Byte”
    // https://docs.confluent.io/current/schema-registry/serializer-formatter.html#wire-format
    assertThat(encoded[0]).isEqualTo((byte) 0);

    var decoded = decoder.deserialize(KAFKA_TOPIC, encoded);

    assertThat(decoded).isEqualTo(sensorState);
  }
}
