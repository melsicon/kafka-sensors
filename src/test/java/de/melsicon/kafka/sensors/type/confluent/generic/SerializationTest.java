package de.melsicon.kafka.sensors.type.confluent.generic;

import static com.google.common.truth.Truth.assertThat;

import de.melsicon.kafka.sensors.testutil.SchemaRegistryRule;
import de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper;
import de.melsicon.kafka.sensors.type.avro.logicaltypes.InstantMicroHelper;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer;
import java.time.Instant;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
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

  private static @MonotonicNonNull Serializer<GenericRecord> encoder;
  private static @MonotonicNonNull Deserializer<GenericRecord> decoder;

  @BeforeClass
  @EnsuresNonNull({"encoder", "decoder"})
  public static void before() {
    encoder = new GenericAvroSerializer();
    encoder.configure(registryTestResource.configs(), /* isKey= */ false);

    decoder = new GenericAvroDeserializer(SchemaHelper.SENSOR_STATE_SCHEMA);
    decoder.configure(registryTestResource.configs(), /* isKey= */ false);
  }

  @AfterClass
  @RequiresNonNull({"encoder", "decoder"})
  public static void after() {
    encoder.close();
    decoder.close();
  }

  private static GenericRecord createSensorState() {
    return new GenericRecordBuilder(SchemaHelper.SENSOR_STATE_SCHEMA)
        .set(SchemaHelper.FIELD_ID, "7331")
        .set(SchemaHelper.FIELD_TIME, InstantMicroHelper.instant2Micros(INSTANT))
        .set(SchemaHelper.FIELD_STATE, SchemaHelper.ENUM_OFF)
        .build();
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
