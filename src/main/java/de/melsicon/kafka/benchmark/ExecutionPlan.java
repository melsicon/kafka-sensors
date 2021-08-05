package de.melsicon.kafka.benchmark;

import static de.melsicon.kafka.serde.Name.AVRO_DIRECT;
import static de.melsicon.kafka.serde.Name.AVRO_GENERIC;
import static de.melsicon.kafka.serde.Name.AVRO_REFLECT;
import static de.melsicon.kafka.serde.Name.AVRO_SPECIFIC;
import static de.melsicon.kafka.serde.Name.CONFLUENT_DIRECT;
import static de.melsicon.kafka.serde.Name.CONFLUENT_GENERIC;
import static de.melsicon.kafka.serde.Name.CONFLUENT_JSON;
import static de.melsicon.kafka.serde.Name.CONFLUENT_PROTO;
import static de.melsicon.kafka.serde.Name.CONFLUENT_REFLECT;
import static de.melsicon.kafka.serde.Name.CONFLUENT_SPECIFIC;
import static de.melsicon.kafka.serde.Name.ION_BINARY;
import static de.melsicon.kafka.serde.Name.ION_TEXT;
import static de.melsicon.kafka.serde.Name.JSON;
import static de.melsicon.kafka.serde.Name.PROTO;
import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.kafka.benchmark.serdes.BenchComponent;
import de.melsicon.kafka.benchmark.serdes.Constants;
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

  private final BenchComponent benchComponent;

  @Param({
    PROTO,
    JSON,
    AVRO_SPECIFIC,
    AVRO_DIRECT,
    AVRO_GENERIC,
    AVRO_REFLECT,
    CONFLUENT_SPECIFIC,
    CONFLUENT_DIRECT,
    CONFLUENT_GENERIC,
    CONFLUENT_REFLECT,
    CONFLUENT_JSON,
    CONFLUENT_PROTO,
    ION_BINARY,
    ION_TEXT
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
