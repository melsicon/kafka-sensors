package de.melsicon.kafka.sensors.benchmark;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.kafka.sensors.benchmark.context.BenchComponent;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Name;
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
  private static final String REGISTRY_SCOPE = "benchmark";
  private static final String REGISTRY_URL = "mock://" + REGISTRY_SCOPE;

  private final BenchComponent benchComponent;

  @Param({
    Name.PROTO,
    Name.JSON,
    Name.AVRO_SPECIFIC,
    Name.AVRO_DIRECT,
    Name.AVRO_GENERIC,
    Name.AVRO_REFLECT,
    Name.CONFLUENT_SPECIFIC,
    Name.CONFLUENT_DIRECT,
    Name.CONFLUENT_GENERIC,
    Name.CONFLUENT_REFLECT,
    Name.CONFLUENT_JSON,
    Name.CONFLUENT_PROTO,
    Name.ION_BINARY,
    Name.ION_TEXT
  })
  public @MonotonicNonNull String serdes;

  public @MonotonicNonNull Serializer<SensorStateWithDuration> serializer;
  public @MonotonicNonNull Deserializer<SensorStateWithDuration> deserializer;
  public @MonotonicNonNull SensorStateWithDuration data;
  public byte @MonotonicNonNull [] serialized;
  private @MonotonicNonNull SchemaRegistryClient registryClient;

  public ExecutionPlan() {
    this.benchComponent = BenchComponent.create();
  }

  @Setup(Level.Iteration)
  @RequiresNonNull("serdes")
  @EnsuresNonNull({"serializer", "deserializer", "data", "serialized", "registryClient"})
  public void setup() {
    registryClient = MockSchemaRegistry.getClientForScope(REGISTRY_SCOPE);
    var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, REGISTRY_URL);

    var iterationComponent =
        benchComponent.iterationComponentFactory().newIterationComponent(serdes, serdeConfig);

    serializer = iterationComponent.serializer();
    deserializer = iterationComponent.deserializer();

    data = Constants.createData();

    serialized = serializer.serialize(Constants.TOPIC, data);
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
