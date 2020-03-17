package de.melsicon.kafka.topology;

import static com.google.common.truth.Truth.assertThat;
import static de.melsicon.kafka.topology.TopologyTestHelper.APPLICATION_ID;
import static de.melsicon.kafka.topology.TopologyTestHelper.INPUT_TOPIC;
import static de.melsicon.kafka.topology.TopologyTestHelper.PARTITIONS;
import static de.melsicon.kafka.topology.TopologyTestHelper.REGISTRY_SCOPE;
import static de.melsicon.kafka.topology.TopologyTestHelper.REPLICATION_FACTOR;
import static de.melsicon.kafka.topology.TopologyTestHelper.RESULT_TOPIC;
import static de.melsicon.kafka.topology.TopologyTestHelper.newKafkaTestResource;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.NUM_STREAM_THREADS_CONFIG;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import de.melsicon.kafka.testutil.SchemaRegistryRule;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
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
  private TopologyTestDriver testDriver;
  private TestInputTopic<String, SensorState> inputTopic;
  private TestOutputTopic<String, SensorStateWithDuration> outputTopic;

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

  private static SensorStateSerdes[] serdes() {
    var specificMapper = SpecificMapper.instance();
    return new SensorStateSerdes[] {
      new de.melsicon.kafka.serde.json.JsonSerdes(),
      new de.melsicon.kafka.serde.proto.ProtoSerdes(),
      new de.melsicon.kafka.serde.avro.AvroSerdes(specificMapper),
      new de.melsicon.kafka.serde.confluent.AvroSerdes(specificMapper)
    };
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<?> parameters() {
    var serdes = serdes();
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

  private static Properties settings() {
    var settings = new Properties();
    settings.put(APPLICATION_ID_CONFIG, APPLICATION_ID);
    settings.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_TEST_RESOURCE.getKafkaConnectString());

    settings.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    settings.put(NUM_STREAM_THREADS_CONFIG, PARTITIONS);

    return settings;
  }

  @Before
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

    var topologyFactory =
        new TopologyFactory()
            .createTopology(INPUT_TOPIC, RESULT_TOPIC, inputSerde, storeSerde, resultSerde);

    var settings = settings();

    testDriver = new TopologyTestDriver(topologyFactory, settings);

    inputTopic =
        testDriver.createInputTopic(INPUT_TOPIC, new StringSerializer(), inputSerde.serializer());
    outputTopic =
        testDriver.createOutputTopic(
            RESULT_TOPIC, new StringDeserializer(), resultSerde.deserializer());
  }

  @After
  public void after() {
    testDriver.close();
  }

  private void pipeState(SensorState sensorState) {
    inputTopic.pipeInput(sensorState.getId(), sensorState);
  }

  @Test
  public void testTopology() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    pipeState(initialState);

    var result1 = outputTopic.readKeyValue();

    assertThat(result1.value).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.ON).build();

    pipeState(newState);

    var result2 = outputTopic.readKeyValue();

    assertThat(result2.value).isNotNull();
    assertThat(result2.value.getEvent()).isEqualTo(initialState);
    assertThat(result2.value.getDuration()).isEqualTo(Duration.ofSeconds(30));
  }

  @Test
  public void testRepeated() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    pipeState(initialState);

    var result1 = outputTopic.readKeyValue();

    assertThat(result1.value).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.OFF).build();

    pipeState(newState);

    var result2 = outputTopic.readKeyValue();

    assertThat(result2.value).isNotNull();
    assertThat(result2.value.getEvent()).isEqualTo(initialState);
    assertThat(result2.value.getDuration()).isEqualTo(Duration.ofSeconds(30));

    var next2 = next.plusSeconds(30);
    var newState2 = SensorState.builder().setId("7331").setTime(next2).setState(State.ON).build();

    pipeState(newState2);

    var result3 = outputTopic.readKeyValue();

    assertThat(result3.value).isNotNull();
    assertThat(result3.value.getEvent()).isEqualTo(initialState);
    assertThat(result3.value.getDuration()).isEqualTo(Duration.ofSeconds(60));

    var next3 = next2.plusSeconds(15);
    var newState3 = SensorState.builder().setId("7331").setTime(next3).setState(State.OFF).build();

    pipeState(newState3);

    var result4 = outputTopic.readKeyValue();

    assertThat(result4.value).isNotNull();
    assertThat(result4.value.getEvent()).isEqualTo(newState2);
    assertThat(result4.value.getDuration()).isEqualTo(Duration.ofSeconds(15));
  }

  @Test
  public void testTombstone() {
    inputTopic.pipeInput("7331", null);

    var result = outputTopic.readKeyValue();

    assertThat(result.value).isNull();
  }
}
