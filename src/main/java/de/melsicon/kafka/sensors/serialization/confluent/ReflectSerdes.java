package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class ReflectSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState,
          de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorStateWithDuration>
      mapper;

  @Inject
  /* package */ ReflectSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState,
              de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer =
        new ReflectionAvroSerializer<
            de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer =
        new ReflectionAvroDeserializer<>(
            de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new ReflectionAvroSerializer<
            de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer =
        new ReflectionAvroDeserializer<>(
            de.melsicon.kafka.sensors.serialization.confluent_reflect.SensorStateWithDuration
                .class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
