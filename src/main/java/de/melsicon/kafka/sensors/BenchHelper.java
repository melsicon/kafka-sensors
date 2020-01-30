package de.melsicon.kafka.sensors;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import de.melsicon.annotation.Initializer;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.GenericMapper;
import de.melsicon.kafka.serde.avromapper.ReflectMapper;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import java.time.Duration;
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

  private static Serde<SensorStateWithDuration> createSerde(SerType serdes) {
    SensorStateSerdes serdeFactory;
    switch (serdes) {
      case JSON:
        serdeFactory = new de.melsicon.kafka.serde.json.JsonSerdes();
        break;
      case PROTO:
        serdeFactory = new de.melsicon.kafka.serde.proto.ProtoSerdes();
        break;
      case AVRO:
        serdeFactory = new de.melsicon.kafka.serde.avro.AvroSerdes(SpecificMapper.instance());
        break;
      case AVRO_REFLECT:
        serdeFactory = new de.melsicon.kafka.serde.avro.ReflectSerdes(ReflectMapper.instance());
        break;
      case AVRO_GENERIC:
        serdeFactory = new de.melsicon.kafka.serde.avro.GenericSerdes(GenericMapper.instance());
        break;
      case CONFLUENT:
        serdeFactory = new de.melsicon.kafka.serde.confluent.AvroSerdes(SpecificMapper.instance());
        break;
      case CONFLUENT_REFLECT:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.ReflectSerdes(ReflectMapper.instance());
        break;
      case CONFLUENT_GENERIC:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.GenericSerdes(GenericMapper.instance());
        break;
      default:
        throw new UnsupportedOperationException("Unknown type " + serdes.name());
    }
    return serdeFactory.createSensorStateWithDurationSerde();
  }

  private static SensorStateWithDuration createData() {
    var instant = Instant.ofEpochSecond(443634300L);

    var event =
        SensorState.builder()
            .setId("7331")
            .setTime(instant)
            .setState(SensorState.State.OFF)
            .build();

    return SensorStateWithDuration.builder()
        .setEvent(event)
        .setDuration(Duration.ofSeconds(15))
        .build();
  }

  public enum SerType {
    JSON,
    PROTO,
    AVRO,
    AVRO_REFLECT,
    AVRO_GENERIC,
    CONFLUENT,
    CONFLUENT_REFLECT,
    CONFLUENT_GENERIC
  }

  @State(Scope.Benchmark)
  public static class ExecutionPlan {
    private static final String REGISTRY_SCOPE = "test";
    private static final String REGISTRY_URL = "mock://" + REGISTRY_SCOPE;

    @SuppressWarnings("NullAway.Init")
    @Param({
      "JSON",
      "PROTO",
      "AVRO",
      "AVRO_REFLECT",
      "AVRO_GENERIC",
      "CONFLUENT",
      "CONFLUENT_REFLECT",
      "CONFLUENT_GENERIC"
    })
    public SerType serdes;

    public Serializer<SensorStateWithDuration> serializer;
    public Deserializer<SensorStateWithDuration> deserializer;
    public SensorStateWithDuration data;
    public byte[] bytes;

    private SchemaRegistryClient registryClient;

    @Initializer
    @Setup(Level.Iteration)
    public void setup() {
      registryClient = MockSchemaRegistry.getClientForScope(REGISTRY_SCOPE);

      var serde = createSerde(serdes);
      var serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, REGISTRY_URL);
      serde.configure(serdeConfig, /* isKey= */ false);

      serializer = serde.serializer();
      deserializer = serde.deserializer();

      data = createData();

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
