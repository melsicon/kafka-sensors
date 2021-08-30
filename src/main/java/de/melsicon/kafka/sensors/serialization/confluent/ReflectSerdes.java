package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.confluent.reflect.SensorState;
import de.melsicon.kafka.sensors.type.confluent.reflect.SensorStateWithDuration;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  /* package */ ReflectSerdes(SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var serializer = new ReflectionAvroSerializer<SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new ReflectionAvroDeserializer<>(SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new ReflectionAvroSerializer<SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new ReflectionAvroDeserializer<>(SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
