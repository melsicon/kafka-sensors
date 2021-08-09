package de.melsicon.kafka.sensors.serialization.confluent;

import java.time.Instant;

/* package */ final class TestHelper {
  /* package */ static final String REGISTRY_SCOPE = "test";
  /* package */ static final String KAFKA_TOPIC = "topic";
  /* package */ static final Instant INSTANT = Instant.ofEpochSecond(443634300L);

  private TestHelper() {}
}
