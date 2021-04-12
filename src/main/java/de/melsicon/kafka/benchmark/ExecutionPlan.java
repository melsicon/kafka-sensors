package de.melsicon.kafka.benchmark;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.kafka.benchmark.serdes.SerDeType;
import de.melsicon.kafka.benchmark.serdes.SerDes;
import de.melsicon.kafka.model.SensorStateWithDuration;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
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

  @Param public @MonotonicNonNull SerDeType serdes;

  public @MonotonicNonNull Serializer<SensorStateWithDuration> serializer;
  public @MonotonicNonNull Deserializer<SensorStateWithDuration> deserializer;
  public @MonotonicNonNull SensorStateWithDuration data;
  public byte @MonotonicNonNull [] serialized;

  private @MonotonicNonNull SchemaRegistryClient registryClient;

  @Setup(Level.Iteration)
  @RequiresNonNull("serdes")
  @EnsuresNonNull({"serializer", "deserializer", "data", "serialized", "registryClient"})
  public void setup() {
    registryClient = MockSchemaRegistry.getClientForScope(REGISTRY_SCOPE);

    var serde = SerDes.createSerde(serdes);
    var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, REGISTRY_URL);
    serde.configure(serdeConfig, /* isKey= */ false);

    serializer = serde.serializer();
    deserializer = serde.deserializer();

    data = SerDes.createData();

    serialized = serializer.serialize(SerDes.TOPIC, data);
  }

  @TearDown(Level.Iteration)
  @RequiresNonNull({"serializer", "deserializer", "registryClient"})
  public void tearDown() {
    serializer.close();
    deserializer.close();

    registryClient.reset();
    MockSchemaRegistry.dropScope(REGISTRY_SCOPE);
  }
}
