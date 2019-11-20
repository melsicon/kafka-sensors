package de.melsicon.kafka.testutil;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class SerdeWithRegistryRule implements TestRule {
  private final Supplier<Serde<SensorState>> inputSerdes;
  private final Supplier<Serde<SensorState>> storeSerdes;
  private final Supplier<Serde<SensorStateWithDuration>> resultSerdes;

  private final String registryScope;

  private SchemaRegistryClient registryClient;

  public SerdeWithRegistryRule(
      Supplier<Serde<SensorState>> inputSerdes,
      Supplier<Serde<SensorState>> storeSerdes,
      Supplier<Serde<SensorStateWithDuration>> resultSerdes,
      String registryScope) {
    this.inputSerdes = inputSerdes;
    this.storeSerdes = storeSerdes;
    this.resultSerdes = resultSerdes;
    this.registryScope = registryScope;
  }

  public String registryUrl() {
    return "mock://" + registryScope;
  }

  public Serde<SensorState> createInputSerde(boolean forRecordKeys) {
    var serde = inputSerdes.get();
    configureSerde(serde);
    return serde;
  }

  public Serde<SensorState> createstoreSerde(boolean forRecordKeys) {
    var serde = storeSerdes.get();
    configureSerde(serde);
    return serde;
  }

  public Serde<SensorStateWithDuration> createResultSerde(boolean forRecordKeys) {
    var serde = resultSerdes.get();
    configureSerde(serde);
    return serde;
  }

  private void configureSerde(Serde<?> serde) {
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
