package de.melsicon.kafka.sensors.topology;

import com.google.common.base.Splitter;
import de.melsicon.kafka.sensors.configuration.KafkaConfiguration;
import java.util.Properties;
import net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig;
import net.mguenther.kafka.junit.EmbeddedKafkaConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

/* package */ final class TopologyTestHelper {
  /* package */ static final String INPUT_TOPIC = "input-topic";
  /* package */ static final String RESULT_TOPIC = "result-topic";
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

  /* package */ static Properties settings(EmbeddedKafkaRule kafkaTestResource) {
    var settings = new Properties();
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaTestResource.getBrokerList());
    settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    settings.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, PARTITIONS);

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
