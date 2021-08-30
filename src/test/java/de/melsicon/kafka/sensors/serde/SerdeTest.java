package de.melsicon.kafka.sensors.serde;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;

import com.google.common.collect.ImmutableCollection;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.testutil.SchemaRegistryRule;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class SerdeTest {
  @Rule public final SchemaRegistryRule registryTestResource;

  private final String description;
  private final Supplier<Serde<SensorState>> inputSerdes;
  private final Supplier<Serde<SensorState>> resultSerdes;

  private @MonotonicNonNull Serializer<SensorState> serializer;
  private @MonotonicNonNull Deserializer<SensorState> deserializer;

  public SerdeTest(
      String description,
      Supplier<Serde<SensorState>> inputSerdes,
      Supplier<Serde<SensorState>> resultSerdes) {
    this.description = description;
    this.inputSerdes = Objects.requireNonNull(inputSerdes, "input serde supplier missing");
    this.resultSerdes = Objects.requireNonNull(resultSerdes, "result serde supplier missing");
    this.registryTestResource = new SchemaRegistryRule();
  }

  @Parameters(name = "{index}: {0}")
  public static ImmutableCollection<?> parameters() {
    return TestHelper.parameters();
  }

  @Before
  @EnsuresNonNull({"serializer", "deserializer"})
  public void before() {
    var inputSerde = inputSerdes.get();
    registryTestResource.configureSerde(inputSerde);
    serializer = inputSerde.serializer();

    var resultSerde = resultSerdes.get();
    registryTestResource.configureSerde(resultSerde);
    deserializer = resultSerde.deserializer();
  }

  @After
  @RequiresNonNull({"serializer", "deserializer"})
  public void after() {
    serializer.close();
    deserializer.close();
  }

  @Test
  @RequiresNonNull({"serializer", "deserializer"})
  public void compatability() {
    var sensorState = TestHelper.standardSensorState();

    var encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, sensorState);
    assertThat(encoded).isNotEmpty();

    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, encoded);
    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  @RequiresNonNull("serializer")
  public void nullEncoding() {
    @SuppressWarnings("nullness:argument") // Serializer is not annotated
    var encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, null);
    assertThat(encoded == null || encoded.length == 0).isTrue();
  }

  @Test
  @RequiresNonNull("deserializer")
  public void nullDecoding() {
    @SuppressWarnings("nullness:argument") // Deserializer is not annotated
    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, null);
    assertThat(decoded).isNull();
  }

  @Test
  @RequiresNonNull("deserializer")
  public void emptyDecoding() {
    assume().that(description).doesNotContain(" - confluent");

    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, new byte[0]);
    assertThat(decoded).isNull();
  }
}
