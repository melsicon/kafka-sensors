package de.melsicon.kafka.serde;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;
import static de.melsicon.kafka.serde.TestHelper.REGISTRY_SCOPE;

import com.google.common.collect.ImmutableCollection;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("nullness:initialization.fields.uninitialized") // Initialized in before
@RunWith(Parameterized.class)
public final class SerdeWithDurationTest {
  @Rule public final SchemaRegistryRule registryTestResource;

  private final String description;
  private final Supplier<Serde<SensorStateWithDuration>> inputSerdes;
  private final Supplier<Serde<SensorStateWithDuration>> resultSerdes;

  private Serializer<SensorStateWithDuration> serializer;
  private Deserializer<SensorStateWithDuration> deserializer;

  public SerdeWithDurationTest(
      String description,
      Supplier<Serde<SensorStateWithDuration>> inputSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes) {
    this.description = description;
    this.inputSerdes = Objects.requireNonNull(inputSerdes, "input serde supplier missing");
    this.resultSerdes = Objects.requireNonNull(resultSerdes, "result serde supplier missing");
    this.registryTestResource = new SchemaRegistryRule(REGISTRY_SCOPE);
  }

  @Parameters(name = "{index}: {0}")
  public static ImmutableCollection<?> parameters() {
    return TestHelper.parametersWithDuration();
  }

  @Before
  public void before() {
    var inputSerde = inputSerdes.get();
    registryTestResource.configureSerde(inputSerde);
    serializer = inputSerde.serializer();

    var resultSerde = resultSerdes.get();
    registryTestResource.configureSerde(resultSerde);
    deserializer = resultSerde.deserializer();
  }

  @After
  public void after() {
    serializer.close();
    deserializer.close();
  }

  @Test
  public void compatability() {
    var sensorState = TestHelper.standardSensorStateWithDuration();

    var encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, sensorState);
    assertThat(encoded).isNotEmpty();

    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, encoded);
    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  public void nullEncoding() {
    @SuppressWarnings("nullness:argument.type.incompatible") // Serializer is not annotated
    var encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, null);
    assertThat(encoded == null || encoded.length == 0).isTrue();
  }

  @Test
  public void nullDecoding() {
    @SuppressWarnings("nullness:argument.type.incompatible") // Deserializer is not annotated
    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, null);
    assertThat(decoded).isNull();
  }

  @Test
  public void emptyDecoding() {
    assume().that(description).doesNotContain(" - confluent");
    var decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, new byte[0]);
    assertThat(decoded).isNull();
  }
}
