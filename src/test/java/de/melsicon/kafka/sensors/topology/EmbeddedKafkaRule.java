package de.melsicon.kafka.sensors.topology;

import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class EmbeddedKafkaRule extends EmbeddedKafkaCluster implements TestRule {
  public EmbeddedKafkaRule(EmbeddedKafkaClusterConfig kafkaClusterConfig) {
    super(kafkaClusterConfig);
  }

  private void before() {
    start();
  }

  private void after() {
    stop();
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        before();
        try {
          base.evaluate();
        } finally {
          after();
        }
      }
    };
  }
}
