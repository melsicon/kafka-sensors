package de.melsicon.kafka.sensors.testutil;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class SchemaRegistryRule implements TestRule {
  private static final String REGISTRY_SCOPE = "test";

  private final String registryScope;
  private final Map<String, String> configs;

  private @MonotonicNonNull SchemaRegistryClient registryClient;

  public SchemaRegistryRule() {
    this(REGISTRY_SCOPE);
  }

  public SchemaRegistryRule(String registryScope) {
    this.registryScope = registryScope;
    var registryUrl = "mock://" + this.registryScope;
    this.configs = Map.of(SCHEMA_REGISTRY_URL_CONFIG, registryUrl);
  }

  public Map<String, String> configs() {
    return configs;
  }

  public void configureSerializer(Serializer<?> serializer) {
    serializer.configure(configs, /* isKey= */ false);
  }

  public void configureDeserializer(Deserializer<?> deserializer) {
    deserializer.configure(configs, /* isKey= */ false);
  }

  @RequiresNonNull("registryClient")
  public SchemaRegistryClient client() {
    // assert registryClient != null : "@AssumeAssertion(nullness): before not called";
    return registryClient;
  }

  public void configureSerde(Serde<?> serde) {
    serde.configure(configs, /* isKey= */ false);
  }

  @EnsuresNonNull("registryClient")
  private void before() {
    registryClient = MockSchemaRegistry.getClientForScope(registryScope);
  }

  @RequiresNonNull("registryClient")
  private void after() {
    // assert registryClient != null : "@AssumeAssertion(nullness): before() not called";
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
