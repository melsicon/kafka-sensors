package de.melsicon.kafka.benchmark.serdes;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.GenericMapper;
import de.melsicon.kafka.serde.avromapper.ReflectMapper;
import de.melsicon.kafka.serde.avromapper.SpecificDirectMapper;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
import de.melsicon.kafka.serde.confluentmapper.ConfluentGenericMapper;
import de.melsicon.kafka.serde.confluentmapper.ConfluentJsonMapper;
import de.melsicon.kafka.serde.confluentmapper.ConfluentReflectMapper;
import de.melsicon.kafka.serde.ion.IonSerdes;
import de.melsicon.kafka.serde.proto.ProtoMapper;
import java.time.Duration;
import java.time.Instant;
import org.apache.kafka.common.serialization.Serde;

public final class SerDes {
  public static final String TOPIC = "topic";

  private SerDes() {}

  public static Serde<SensorStateWithDuration> createSerde(SerDeType serdes) {
    SensorStateSerdes serdeFactory;
    switch (serdes) {
      case JSON:
        serdeFactory = new de.melsicon.kafka.serde.json.JsonSerdes();
        break;
      case PROTO:
        serdeFactory = new de.melsicon.kafka.serde.proto.ProtoSerdes();
        break;
      case AVRO:
        serdeFactory = new de.melsicon.kafka.serde.avro.SpecificSerdes(SpecificMapper.instance());
        break;
      case AVRO_DIRECT:
        serdeFactory =
            new de.melsicon.kafka.serde.avro.SpecificSerdes(SpecificDirectMapper.instance());
        break;
      case AVRO_REFLECT:
        serdeFactory = new de.melsicon.kafka.serde.avro.ReflectSerdes(ReflectMapper.instance());
        break;
      case AVRO_GENERIC:
        serdeFactory = new de.melsicon.kafka.serde.avro.GenericSerdes(GenericMapper.instance());
        break;
      case CONFLUENT_SPECIFIC:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.SpecificSerdes(SpecificMapper.instance());
        break;
      case CONFLUENT_DIRECT:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.SpecificSerdes(SpecificDirectMapper.instance());
        break;
      case CONFLUENT_REFLECT:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.ReflectSerdes(ConfluentReflectMapper.instance());
        break;
      case CONFLUENT_GENERIC:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.GenericSerdes(ConfluentGenericMapper.instance());
        break;
      case CONFLUENT_JSON:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.JsonSerdes(ConfluentJsonMapper.instance());
        break;
      case CONFLUENT_PROTO:
        serdeFactory = new de.melsicon.kafka.serde.confluent.ProtoSerdes(ProtoMapper.instance());
        break;
      case ION_TEXT:
        serdeFactory = IonSerdes.textSerdes();
        break;
      case ION_BINARY:
        serdeFactory = IonSerdes.binarySerdes();
        break;
      default:
        throw new UnsupportedOperationException("Unknown type " + serdes.name());
    }
    return serdeFactory.createSensorStateWithDurationSerde();
  }

  public static SensorStateWithDuration createData() {
    var instant = Instant.ofEpochSecond(443634300L);

    var event = SensorState.builder().id("7331").time(instant).state(SensorState.State.OFF).build();

    return SensorStateWithDuration.builder().event(event).duration(Duration.ofSeconds(15)).build();
  }
}
