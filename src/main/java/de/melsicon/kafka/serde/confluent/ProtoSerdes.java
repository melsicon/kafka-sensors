package de.melsicon.kafka.serde.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import de.melsicon.kafka.serde.proto.ProtoMapper;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ProtoSerdes implements SensorStateSerdes {
  private final ProtoMapper mapper;

  @Inject
  public ProtoSerdes(ProtoMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "confluent-proto";
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_PROTO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new KafkaProtobufSerializer<de.melsicon.kafka.sensors.v1.SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer =
        new KafkaProtobufDeserializer<>(de.melsicon.kafka.sensors.v1.SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new KafkaProtobufSerializer<de.melsicon.kafka.sensors.v1.SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer =
        new KafkaProtobufDeserializer<>(de.melsicon.kafka.sensors.v1.SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
