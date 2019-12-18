package de.melsicon.kafka.serde;

import static de.melsicon.kafka.serde.TestHelper.REGISTRY_SCOPE;
import static org.assertj.core.api.Assertions.assertThat;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.avro.AvroSerdes;
import de.melsicon.kafka.serde.json.JsonSerdes;
import de.melsicon.kafka.serde.proto.ProtoSerdes;
import de.melsicon.kafka.testutil.SerdeWithRegistryRule;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class SerdeTest {
  @Rule public final SerdeWithRegistryRule serdeTestResource;

  public SerdeTest(
      String description,
      Supplier<Serde<SensorState>> inputSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes) {
    Objects.requireNonNull(inputSerdes, "input serde supplier missing");
    Objects.requireNonNull(resultSerdes, "result serde supplier missing");
    this.serdeTestResource =
        new SerdeWithRegistryRule(inputSerdes, () -> null, resultSerdes, REGISTRY_SCOPE);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<?> serdes() {
    var serdes = List.of(new AvroSerdes(), new ProtoSerdes(), new JsonSerdes());
    var combinations = new ArrayList<Object[]>(serdes.size() * serdes.size());
    for (var inputSerdes : serdes) {
      for (var resultSerdes : serdes) {
        var o = new Object[3];
        o[0] = inputSerdes.name() + " - " + resultSerdes.name();
        o[1] = (Supplier<Serde<SensorState>>) inputSerdes::createSensorStateSerde;
        o[2] =
            (Supplier<Serde<SensorStateWithDuration>>)
                resultSerdes::createSensorStateWithDurationSerde;
        combinations.add(o);
      }
    }

    return combinations;
  }

  @Test
  public void decoding() {
    var sensorState = TestHelper.standardSensorState();
    SensorState decoded;

    try (var serde = serdeTestResource.createInputSerde(false)) {
      byte[] encoded;
      try (var serializer = serde.serializer()) {
        encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, sensorState);
      }

      try (var deserializer = serde.deserializer()) {
        decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, encoded);
      }
    }

    assertThat(decoded).isEqualTo(sensorState);
  }

  @Test
  public void decodingWithDuration() {
    var sensorState = TestHelper.standardSensorState();
    var event =
        SensorStateWithDuration.builder()
            .setEvent(sensorState)
            .setDuration(Duration.ofSeconds(30))
            .build();

    SensorStateWithDuration decoded;

    try (var serde = serdeTestResource.createResultSerde(false)) {
      byte[] encoded;
      try (var serializer = serde.serializer()) {
        encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, event);
      }

      try (var deserializer = serde.deserializer()) {
        decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, encoded);
      }
    }

    assertThat(decoded).isEqualTo(event);
  }

  @Test
  public void nullHandling() {
    SensorState decoded;

    try (var serde = serdeTestResource.createInputSerde(false)) {
      byte[] encoded;
      try (var serializer = serde.serializer()) {
        encoded = serializer.serialize(TestHelper.KAFKA_TOPIC, null);
      }

      assertThat(encoded).isNullOrEmpty();

      try (var deserializer = serde.deserializer()) {
        decoded = deserializer.deserialize(TestHelper.KAFKA_TOPIC, encoded);
      }
    }

    assertThat(decoded).isNull();
  }
}
