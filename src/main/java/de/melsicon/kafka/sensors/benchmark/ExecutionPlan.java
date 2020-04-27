package de.melsicon.kafka.sensors.benchmark;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serdes.SerDeType;
import de.melsicon.kafka.sensors.serdes.SerDes;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

@State(Scope.Benchmark)
public class ExecutionPlan {
  private static final String REGISTRY_SCOPE = "test";
  private static final String REGISTRY_URL = "mock://" + REGISTRY_SCOPE;

  @Param public SerDeType serdes;

  public Serializer<SensorStateWithDuration> serializer;
  public Deserializer<SensorStateWithDuration> deserializer;
  public SensorStateWithDuration data;
  public byte[] bytes;

  private SchemaRegistryClient registryClient;

  @Initializer
  @Setup(Level.Iteration)
  public void setup() {
    registryClient = MockSchemaRegistry.getClientForScope(REGISTRY_SCOPE);

    var serde = SerDes.createSerde(serdes);
    var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, REGISTRY_URL);
    serde.configure(serdeConfig, /* isKey= */ false);

    serializer = serde.serializer();
    deserializer = serde.deserializer();

    data = SerDes.createData();

    bytes = serializer.serialize(SerDes.TOPIC, data);
  }

  @TearDown(Level.Iteration)
  public void tearDown() {
    serializer.close();
    deserializer.close();

    registryClient.reset();
    MockSchemaRegistry.dropScope(REGISTRY_SCOPE);
  }
}
