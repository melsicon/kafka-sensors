package de.melsicon.kafka.sensors.serialization.proto;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.proto.ProtoDeserializer;
import de.melsicon.kafka.sensors.type.proto.ProtoSerializer;
import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorStateWithDuration;
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
    return Format.PROTO;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var serializer = new ProtoSerializer<SensorState>();
    var deserializer = new ProtoDeserializer<>(SensorState.parser());

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new ProtoSerializer<SensorStateWithDuration>();
    var deserializer = new ProtoDeserializer<>(SensorStateWithDuration.parser());

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
