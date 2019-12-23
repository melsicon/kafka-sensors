package de.melsicon.kafka.sensors;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avro.AvroSerdes;
import de.melsicon.kafka.serde.json.JsonSerdes;
import de.melsicon.kafka.serde.proto.ProtoSerdes;
import de.melsicon.kafka.serde.reflect.ReflectSerdes;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.time.Instant;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

public final class BenchHelper {
  private BenchHelper() {}

  private static Serde<SensorState> createSensorStateSerde(SerType serdes) {
    SensorStateSerdes serdeFactory;
    switch (serdes) {
      case AVRO:
        serdeFactory = new AvroSerdes();
        break;
      case JSON:
        serdeFactory = new JsonSerdes();
        break;
      case PROTO:
        serdeFactory = new ProtoSerdes();
        break;
      case REFLECT:
        serdeFactory = new ReflectSerdes();
        break;
      default:
        throw new UnsupportedOperationException("Unknown type " + serdes.name());
    }
    return serdeFactory.createSensorStateSerde();
  }

  private static SensorState createSensorState() {
    var instant = Instant.ofEpochSecond(443634300L);

    return SensorState.builder()
        .setId("7331")
        .setTime(instant)
        .setState(SensorState.State.OFF)
        .build();
  }

  public enum SerType {
    AVRO,
    JSON,
    PROTO,
    REFLECT
  }

  @State(Scope.Benchmark)
  public static class ExecutionPlan {
    private static final String REGISTRY_SCOPE = "test";
    private static final String REGISTRY_URL = "mock://" + REGISTRY_SCOPE;

    @SuppressWarnings("NullAway.Init")
    @Param({"AVRO", "JSON", "PROTO", "REFLECT"})
    public SerType serdes;

    public Serializer<SensorState> serializer;
    public Deserializer<SensorState> deserializer;
    public SensorState data;
    public byte[] bytes;

    private SchemaRegistryClient registryClient;

    @Initializer
    @Setup(Level.Iteration)
    public void setup() {
      registryClient = MockSchemaRegistry.getClientForScope(REGISTRY_SCOPE);

      var serde = createSensorStateSerde(serdes);
      var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, REGISTRY_URL);
      serde.configure(serdeConfig, /* isKey= */ false);

      serializer = serde.serializer();
      deserializer = serde.deserializer();

      data = createSensorState();

      bytes = serializer.serialize(null, data);
    }

    @TearDown
    public void tearDown() {
      serializer.close();
      deserializer.close();

      registryClient.reset();
      MockSchemaRegistry.dropScope(REGISTRY_SCOPE);
    }
  }
}
