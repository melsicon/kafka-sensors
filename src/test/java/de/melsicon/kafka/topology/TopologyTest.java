package de.melsicon.kafka.topology;

import static com.google.common.truth.Truth.assertThat;
import static de.melsicon.kafka.topology.TopologyTestHelper.INPUT_TOPIC;
import static de.melsicon.kafka.topology.TopologyTestHelper.PARTITIONS;
import static de.melsicon.kafka.topology.TopologyTestHelper.REGISTRY_SCOPE;
import static de.melsicon.kafka.topology.TopologyTestHelper.REPLICATION_FACTOR;
import static de.melsicon.kafka.topology.TopologyTestHelper.RESULT_TOPIC;
import static de.melsicon.kafka.topology.TopologyTestHelper.newKafkaTestResource;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import de.melsicon.kafka.context.TestComponent;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public final class TopologyTest {
  @ClassRule
  public static final SharedKafkaTestResource KAFKA_TEST_RESOURCE = newKafkaTestResource();

  @Rule public final SchemaRegistryRule registryTestResource;
  private final Supplier<Serde<SensorState>> inputSerdes;
  private final Supplier<Serde<SensorState>> storeSerdes;
  private final Supplier<Serde<SensorStateWithDuration>> resultSerdes;
  private @MonotonicNonNull TopologyTestDriver testDriver;
  private @MonotonicNonNull TestInputTopic<String, SensorState> inputTopic;
  private @MonotonicNonNull TestOutputTopic<String, SensorStateWithDuration> outputTopic;

  public TopologyTest(
      String description,
      Supplier<Serde<SensorState>> inputSerdes,
      Supplier<Serde<SensorState>> storeSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes) {
    this.inputSerdes = inputSerdes;
    this.storeSerdes = storeSerdes;
    this.resultSerdes = resultSerdes;

    this.registryTestResource = new SchemaRegistryRule(REGISTRY_SCOPE);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<?> parameters() {
    var serdes = TopologyTestHelper.serdes();
    var combinations = new ArrayList<Object[]>(serdes.length * serdes.length * serdes.length);
    for (var inputSerdes : serdes) {
      for (var storeSerdes : serdes) {
        for (var resultSerdes : serdes) {
          var o = new Object[4];
          o[0] = inputSerdes.name() + " - " + storeSerdes.name() + " - " + resultSerdes.name();
          o[1] = (Supplier<Serde<SensorState>>) inputSerdes::createSensorStateSerde;
          o[2] = (Supplier<Serde<SensorState>>) storeSerdes::createSensorStateSerde;
          o[3] =
              (Supplier<Serde<SensorStateWithDuration>>)
                  resultSerdes::createSensorStateWithDurationSerde;
          combinations.add(o);
        }
      }
    }

    return combinations;
  }

  @Before
  @EnsuresNonNull({"testDriver", "inputTopic", "outputTopic"})
  public void before() {
    var inputSerde = inputSerdes.get();
    registryTestResource.configureSerde(inputSerde);

    var storeSerde = storeSerdes.get();
    registryTestResource.configureSerde(storeSerde);

    var resultSerde = resultSerdes.get();
    registryTestResource.configureSerde(resultSerde);

    var kafkaTestUtils = KAFKA_TEST_RESOURCE.getKafkaTestUtils();
    kafkaTestUtils.createTopic(INPUT_TOPIC, PARTITIONS, REPLICATION_FACTOR);
    kafkaTestUtils.createTopic(RESULT_TOPIC, PARTITIONS, REPLICATION_FACTOR);

    var configuration = TopologyTestHelper.configuration(KAFKA_TEST_RESOURCE);
    var settings = TopologyTestHelper.settings(KAFKA_TEST_RESOURCE);
    var testComponent =
        TestComponent.factory()
            .newTestComponent(configuration, inputSerde, storeSerde, resultSerde);
    var topology = testComponent.topology();
    testDriver = new TopologyTestDriver(topology, settings);

    inputTopic =
        testDriver.createInputTopic(INPUT_TOPIC, new StringSerializer(), inputSerde.serializer());
    outputTopic =
        testDriver.createOutputTopic(
            RESULT_TOPIC, new StringDeserializer(), resultSerde.deserializer());
  }

  @After
  @RequiresNonNull("testDriver")
  public void after() {
    testDriver.close();
  }

  @RequiresNonNull("inputTopic")
  private void pipeState(SensorState sensorState) {
    inputTopic.pipeInput(sensorState.getId(), sensorState);
  }

  @Test
  @RequiresNonNull({"inputTopic", "outputTopic"})
  public void testTopology() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState = SensorState.builder().id("7331").time(instant).state(State.OFF).build();

    pipeState(initialState);

    var result1 = outputTopic.readKeyValue();

    assertThat(result1.value).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().id("7331").time(next).state(State.ON).build();

    pipeState(newState);

    var result2 = outputTopic.readKeyValue();

    assertThat(result2.value).isNotNull();
    assertThat(result2.value.getEvent()).isEqualTo(initialState);
    assertThat(result2.value.getDuration()).isEqualTo(Duration.ofSeconds(30));
  }

  @Test
  @RequiresNonNull({"inputTopic", "outputTopic"})
  public void testRepeated() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState = SensorState.builder().id("7331").time(instant).state(State.OFF).build();

    pipeState(initialState);

    var result1 = outputTopic.readKeyValue();

    assertThat(result1.value).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().id("7331").time(next).state(State.OFF).build();

    pipeState(newState);

    var result2 = outputTopic.readKeyValue();

    assertThat(result2.value).isNotNull();
    assertThat(result2.value.getEvent()).isEqualTo(initialState);
    assertThat(result2.value.getDuration()).isEqualTo(Duration.ofSeconds(30));

    var next2 = next.plusSeconds(30);
    var newState2 = SensorState.builder().id("7331").time(next2).state(State.ON).build();

    pipeState(newState2);

    var result3 = outputTopic.readKeyValue();

    assertThat(result3.value).isNotNull();
    assertThat(result3.value.getEvent()).isEqualTo(initialState);
    assertThat(result3.value.getDuration()).isEqualTo(Duration.ofSeconds(60));

    var next3 = next2.plusSeconds(15);
    var newState3 = SensorState.builder().id("7331").time(next3).state(State.OFF).build();

    pipeState(newState3);

    var result4 = outputTopic.readKeyValue();

    assertThat(result4.value).isNotNull();
    assertThat(result4.value.getEvent()).isEqualTo(newState2);
    assertThat(result4.value.getDuration()).isEqualTo(Duration.ofSeconds(15));
  }

  @Test
  @RequiresNonNull({"inputTopic", "outputTopic"})
  @SuppressWarnings("nullness:argument") // TestInputTopic is not annotated
  public void testTombstone() {
    inputTopic.pipeInput("7331", null);

    var result = outputTopic.readKeyValue();

    assertThat(result.value).isNull();
  }
}
