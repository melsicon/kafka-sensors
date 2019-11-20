package de.melsicon.kafka.serde.proto;

import de.melsicon.kafka.sensors.v1.SensorState;
import de.melsicon.kafka.sensors.v1.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class ProtoSerdes implements SensorStateSerdes {
  private final SensorStateMapper mapper;

  @Inject
  public ProtoSerdes() {
    this.mapper = SensorStateMapper.instance();
  }

  @Override
  public String name() {
    return "proto";
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var serializer = new ProtoSerializer<SensorState>();
    var deserializer = new ProtoDeserializer<>(SensorState.parser());

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new ProtoSerializer<SensorStateWithDuration>();
    var deserializer = new ProtoDeserializer<>(SensorStateWithDuration.parser());

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
