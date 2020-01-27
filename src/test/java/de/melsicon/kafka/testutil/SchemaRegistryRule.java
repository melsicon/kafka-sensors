package de.melsicon.kafka.testutil;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import org.apache.kafka.common.serialization.Serde;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class SchemaRegistryRule implements TestRule {
  private final String registryScope;

  private SchemaRegistryClient registryClient;

  public SchemaRegistryRule(String registryScope) {
    this.registryScope = registryScope;
  }

  public String registryUrl() {
    return "mock://" + registryScope;
  }

  public void configureSerde(Serde<?> serde) {
    var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, registryUrl());
    serde.configure(serdeConfig, /* isKey= */ false);
  }

  @Initializer
  private void before() throws Exception {
    registryClient = MockSchemaRegistry.getClientForScope(registryScope);
  }

  private void after() {
    registryClient.reset();
    MockSchemaRegistry.dropScope(registryScope);
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