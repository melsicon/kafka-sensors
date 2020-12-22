package de.melsicon.kafka.topology;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.NUM_STREAM_THREADS_CONFIG;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;

/* package */ final class TopologyTestHelper {
  /* package */ static final String INPUT_TOPIC = "input-topic";
  /* package */ static final String RESULT_TOPIC = "result-topic";
  /* package */ static final String REGISTRY_SCOPE = "test";
  /* package */ static final short REPLICATION_FACTOR = 1;
  /* package */ static final String APPLICATION_ID = "topology-test";
  private static final String AUTO_CREATE_TOPICS_ENABLE_CONFIG = "auto.create.topics.enable";
  private static final int BROKER_COUNT = 2;
  /* package */ static final int PARTITIONS = BROKER_COUNT;

  private TopologyTestHelper() {}

  /* package */ static SharedKafkaTestResource newKafkaTestResource() {
    return new SharedKafkaTestResource()
        .withBrokerProperty(AUTO_CREATE_TOPICS_ENABLE_CONFIG, "false")
        .withBrokers(BROKER_COUNT);
  }

  @SuppressWarnings("UnnecessarilyFullyQualified")
  /* package */ static SensorStateSerdes[] serdes() {
    var specificMapper = SpecificMapper.instance();
    return new SensorStateSerdes[] {
      new de.melsicon.kafka.serde.json.JsonSerdes(),
      new de.melsicon.kafka.serde.proto.ProtoSerdes(),
      new de.melsicon.kafka.serde.avro.SpecificSerdes(specificMapper),
      new de.melsicon.kafka.serde.confluent.SpecificSerdes(specificMapper)
    };
  }

  /* package */ static Properties settings() {
    var settings = new Properties();
    settings.put(APPLICATION_ID_CONFIG, APPLICATION_ID);
    settings.put(
        BOOTSTRAP_SERVERS_CONFIG, TopologyTest.KAFKA_TEST_RESOURCE.getKafkaConnectString());

    settings.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    settings.put(NUM_STREAM_THREADS_CONFIG, PARTITIONS);

    return settings;
  }
}
