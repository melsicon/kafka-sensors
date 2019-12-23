package de.melsicon.kafka.serialization.confluent;

import static org.assertj.core.api.Assertions.assertThat;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.avro.AvroSerdes;
import de.melsicon.kafka.serde.reflect.ReflectSerdes;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

@RunWith(Parameterized.class)
public final class SerializationTest {
  /* package */ static final String REGISTRY_SCOPE = "test";

  @Rule public final SchemaRegistryRule registryTestResource;

  private final Supplier<Serde<SensorStateWithDuration>> inputSerdes;
  private final Supplier<Serde<SensorStateWithDuration>> resultSerdes;

  private Serializer<SensorStateWithDuration> serializer;
  private Deserializer<SensorStateWithDuration> deserializer;

  public SerializationTest(
      String description,
      Supplier<Serde<SensorStateWithDuration>> inputSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes) {
    this.inputSerdes = inputSerdes;
    this.resultSerdes = resultSerdes;

    this.registryTestResource = new SchemaRegistryRule(REGISTRY_SCOPE);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<?> serdes() {
    var serdes = List.of(new AvroSerdes(), new ReflectSerdes());
    var combinations = new ArrayList<Object[]>(serdes.size() * serdes.size());
    for (var inputSerdes : serdes) {
      for (var resultSerdes : serdes) {
        var o = new Object[3];
        o[0] = inputSerdes.name() + " - " + resultSerdes.name();
        o[1] =
            (Supplier<Serde<SensorStateWithDuration>>)
                inputSerdes::createSensorStateWithDurationSerde;
        o[2] =
            (Supplier<Serde<SensorStateWithDuration>>)
                resultSerdes::createSensorStateWithDurationSerde;
        combinations.add(o);
      }
    }

    return combinations;
  }

  @Before
  @Initializer
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
    deserializer.close();
    serializer.close();
  }

  @Test
  public void testTopology() {
    var instant = Instant.ofEpochSecond(443634300L);

    var event = SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    var sensorState =
        SensorStateWithDuration.builder()
            .setEvent(event)
            .setDuration(Duration.ofSeconds(15))
            .build();
    var bytes = serializer.serialize(null, sensorState);

    var decoded = deserializer.deserialize(null, bytes);

    assertThat(decoded).isEqualTo(sensorState);
  }
}
