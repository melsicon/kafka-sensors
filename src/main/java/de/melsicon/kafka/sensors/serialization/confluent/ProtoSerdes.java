package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.confluent.proto.KafkaProtobufDeserializer;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorStateWithDuration;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ProtoSerdes implements SensorStateSerdes {
  private final SensorStateMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  /* package */ ProtoSerdes(SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_PROTO;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var serializer = new KafkaProtobufSerializer<SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new KafkaProtobufDeserializer<>(SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new KafkaProtobufSerializer<SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new KafkaProtobufDeserializer<>(SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
