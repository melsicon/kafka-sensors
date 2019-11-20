package de.melsicon.kafka.topology;

import static de.melsicon.kafka.topology.TestHelper.APPLICATION_ID;
import static de.melsicon.kafka.topology.TestHelper.INPUT_TOPIC;
import static de.melsicon.kafka.topology.TestHelper.PARTITIONS;
import static de.melsicon.kafka.topology.TestHelper.RESULT_TOPIC;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import de.melsicon.annotation.Initializer;
import de.melsicon.annotation.Nullable;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorState.State;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.avro.AvroSerdes;
import de.melsicon.kafka.serde.json.JsonSerdes;
import de.melsicon.kafka.serde.proto.ProtoSerdes;
import de.melsicon.kafka.testutil.SerdeWithRegistryRule;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
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
  public static final SharedKafkaTestResource KAFKA_TEST_RESOURCE =
      TestHelper.newKafkaTestResource();

  @Rule public final SerdeWithRegistryRule serdeTestResource;

  private TopologyTestDriver testDriver;
  private ConsumerRecordFactory<String, SensorState> recordFactory;

  private Serde<SensorStateWithDuration> resultSerde;

  public TopologyTest(
      String description,
      Supplier<Serde<SensorState>> inputSerdes,
      Supplier<Serde<SensorState>> storeSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes) {
    serdeTestResource =
        new SerdeWithRegistryRule(
            inputSerdes, storeSerdes, resultSerdes, TestHelper.REGISTRY_SCOPE);
  }

  @Parameters(name = "{index}: {0}")
  public static Collection<?> serdes() {
    var serdes = List.of(new AvroSerdes(), new ProtoSerdes(), new JsonSerdes());
    var combinations = new ArrayList<Object[]>(serdes.size() * serdes.size());
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
  @Initializer
  public void before() {
    var inputSerde = serdeTestResource.createInputSerde(false);
    var storeSerde = serdeTestResource.createstoreSerde(false);
    resultSerde = serdeTestResource.createResultSerde(false);

    var kafkaTestUtils = KAFKA_TEST_RESOURCE.getKafkaTestUtils();
    kafkaTestUtils.createTopic(INPUT_TOPIC, PARTITIONS, TestHelper.REPLICATION_FACTOR);
    kafkaTestUtils.createTopic(RESULT_TOPIC, PARTITIONS, TestHelper.REPLICATION_FACTOR);

    var topologyFactory =
        new TopologyFactory()
            .createTopology(INPUT_TOPIC, RESULT_TOPIC, inputSerde, storeSerde, resultSerde);

    var settings = new Properties();
    settings.put(APPLICATION_ID_CONFIG, APPLICATION_ID);
    settings.put(BOOTSTRAP_SERVERS_CONFIG, KAFKA_TEST_RESOURCE.getKafkaConnectString());

    testDriver = new TopologyTestDriver(topologyFactory, settings);

    recordFactory =
        new ConsumerRecordFactory<>(INPUT_TOPIC, new StringSerializer(), inputSerde.serializer());
  }

  @After
  public void after() {
    testDriver.close();
  }

  private void pipeState(@Nullable SensorState sensorState) {
    ConsumerRecord<byte[], byte[]> record;
    if (sensorState == null) {
      record = recordFactory.create(sensorState);
    } else {
      record = recordFactory.create(INPUT_TOPIC, sensorState.getId(), sensorState);
    }
    testDriver.pipeInput(record);
  }

  private @Nullable ProducerRecord<@Nullable String, @Nullable SensorStateWithDuration>
      readOutput() {
    return testDriver.readOutput(
        RESULT_TOPIC, new StringDeserializer(), resultSerde.deserializer());
  }

  @Test
  public void testTopology() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    pipeState(initialState);

    var result1 = readOutput();

    assertThat(result1).isNotNull();
    assertThat(Objects.requireNonNull(result1).value()).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.ON).build();

    pipeState(newState);

    var result2 = readOutput();
    assertThat(result2).isNotNull();

    var value = Objects.requireNonNull(result2).value();

    assertThat(value).isNotNull();
    assertThat(value.getEvent()).isEqualTo(initialState);
    assertThat(value.getDuration()).isEqualTo(Duration.ofSeconds(30));
  }

  @Test
  public void testRepeated() {
    var instant = Instant.ofEpochSecond(443634300L);

    var initialState =
        SensorState.builder().setId("7331").setTime(instant).setState(State.OFF).build();

    pipeState(initialState);

    var result1 = readOutput();

    assertThat(result1).isNotNull();
    assertThat(Objects.requireNonNull(result1).value()).isNull();

    var next = instant.plusSeconds(30);
    var newState = SensorState.builder().setId("7331").setTime(next).setState(State.OFF).build();

    pipeState(newState);

    var result2 = readOutput();
    assertThat(result2).isNotNull();

    var value = Objects.requireNonNull(result2).value();

    assertThat(value).isNotNull();
    assertThat(value.getEvent()).isEqualTo(initialState);
    assertThat(value.getDuration()).isEqualTo(Duration.ofSeconds(30));

    var next2 = next.plusSeconds(30);
    var newState2 = SensorState.builder().setId("7331").setTime(next2).setState(State.ON).build();

    pipeState(newState2);

    var result3 = readOutput();
    assertThat(result3).isNotNull();

    var value3 = Objects.requireNonNull(result3).value();

    assertThat(value3).isNotNull();
    assertThat(value3.getEvent()).isEqualTo(initialState);
    assertThat(value3.getDuration()).isEqualTo(Duration.ofSeconds(60));

    var next3 = next2.plusSeconds(15);
    var newState3 = SensorState.builder().setId("7331").setTime(next3).setState(State.OFF).build();

    pipeState(newState3);

    var result4 = readOutput();
    assertThat(result4).isNotNull();

    var value4 = Objects.requireNonNull(result4).value();

    assertThat(value4).isNotNull();
    assertThat(value4.getEvent()).isEqualTo(newState2);
    assertThat(value4.getDuration()).isEqualTo(Duration.ofSeconds(15));
  }

  @Test
  public void testTombstone() {
    assertThatCode(() -> pipeState(null)).doesNotThrowAnyException();

    var result = readOutput();
    assertThat(result).isNotNull();

    var value = Objects.requireNonNull(result).value();
    assertThat(value).isNull();
  }
}
