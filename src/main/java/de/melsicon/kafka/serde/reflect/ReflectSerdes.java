package de.melsicon.kafka.serde.reflect;

import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper mapper;

  @Inject
  public ReflectSerdes() {
    this.mapper = SensorStateMapper.instance();
  }

  @Override
  public String name() {
    return "reflect";
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var serializer = new ReflectionAvroSerializer<SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new ReflectionAvroDeserializer<>(SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new ReflectionAvroSerializer<SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new ReflectionAvroDeserializer<>(SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
