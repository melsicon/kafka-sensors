package de.melsicon.kafka.serde.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ProtoSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.v1.SensorState,
          de.melsicon.kafka.sensors.v1.SensorStateWithDuration>
      mapper;

  @Inject
  /* package */ ProtoSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.v1.SensorState,
              de.melsicon.kafka.sensors.v1.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
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
