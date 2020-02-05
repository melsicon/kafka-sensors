package de.melsicon.kafka.testutil;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
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

  private Map<String, String> configs() {
    return Map.of(SCHEMA_REGISTRY_URL_CONFIG, registryUrl());
  }

  public void configureSerializer(Serializer<?> serializer) {
    serializer.configure(configs(), /* isKey= */ false);
  }

  public void configureDeserializer(Deserializer<?> deserializer) {
    deserializer.configure(configs(), /* isKey= */ false);
  }

  public SchemaRegistryClient client() {
    return registryClient;
  }

  public void configureSerde(Serde<?> serde) {
    serde.configure(configs(), /* isKey= */ false);
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
