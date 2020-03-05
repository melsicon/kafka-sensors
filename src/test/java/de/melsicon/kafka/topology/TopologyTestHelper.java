package de.melsicon.kafka.topology;

import com.salesforce.kafka.test.junit4.SharedKafkaTestResource;

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
}
