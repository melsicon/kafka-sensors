package de.melsicon.kafka.sensors.serdes;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.ConfluentGenericMapper;
import de.melsicon.kafka.serde.avromapper.ConfluentReflectMapper;
import de.melsicon.kafka.serde.avromapper.GenericMapper;
import de.melsicon.kafka.serde.avromapper.ReflectMapper;
import de.melsicon.kafka.serde.avromapper.SpecificMapper;
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
            new de.melsicon.kafka.serde.confluent.ReflectSerdes(ConfluentReflectMapper.instance());
        break;
      case CONFLUENT_GENERIC:
        serdeFactory =
            new de.melsicon.kafka.serde.confluent.GenericSerdes(ConfluentGenericMapper.instance());
        break;
      default:
        throw new UnsupportedOperationException("Unknown type " + serdes.name());
    }
    return serdeFactory.createSensorStateWithDurationSerde();
  }

  public static SensorStateWithDuration createData() {
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
}