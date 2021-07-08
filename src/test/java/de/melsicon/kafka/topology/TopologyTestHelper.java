package de.melsicon.kafka.topology;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.NUM_STREAM_THREADS_CONFIG;

import com.google.common.base.Splitter;
import de.melsicon.kafka.configuration.KafkaConfiguration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import de.melsicon.kafka.testutil.EmbeddedKafkaRule;
import java.util.Properties;
import net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig;
import net.mguenther.kafka.junit.EmbeddedKafkaConfig;
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

  /* package */ static EmbeddedKafkaRule newKafkaTestResource() {
    var kafkaConfig =
        EmbeddedKafkaConfig.brokers()
            .with(AUTO_CREATE_TOPICS_ENABLE_CONFIG, false)
            .withNumberOfBrokers(BROKER_COUNT)
            .build();
    var kafkaClusterConfig =
        EmbeddedKafkaClusterConfig.newClusterConfig().configure(kafkaConfig).build();
    return new EmbeddedKafkaRule(kafkaClusterConfig);
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

  /* package */ static Properties settings(EmbeddedKafkaRule kafkaTestResource) {
    var settings = new Properties();
    settings.put(APPLICATION_ID_CONFIG, APPLICATION_ID);
    settings.put(BOOTSTRAP_SERVERS_CONFIG, kafkaTestResource.getBrokerList());
    settings.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    settings.put(NUM_STREAM_THREADS_CONFIG, PARTITIONS);

    return settings;
  }

  /* package */ static KafkaConfiguration configuration(EmbeddedKafkaRule kafkaTestResource) {

    return KafkaConfiguration.builder()
        .inputTopic(INPUT_TOPIC)
        .resultTopic(RESULT_TOPIC)
        .bootstrapServers(bootstrapServers(kafkaTestResource))
        .build();
  }

  private static Iterable<String> bootstrapServers(EmbeddedKafkaRule kafkaTestResource) {
    return Splitter.on(',').split(kafkaTestResource.getBrokerList());
  }
}
