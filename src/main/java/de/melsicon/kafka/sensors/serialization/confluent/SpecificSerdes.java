package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import org.apache.kafka.common.serialization.Serde;

public final class SpecificSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      mapper;

  @AssistedInject
  /* package */ SpecificSerdes(
      @Assisted
          SensorStateMapper<
                  de.melsicon.kafka.sensors.avro.SensorState,
                  de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
              mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new SpecificAvroSerializer<de.melsicon.kafka.sensors.avro.SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer =
        new SpecificAvroDeserializer<>(de.melsicon.kafka.sensors.avro.SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new SpecificAvroSerializer<de.melsicon.kafka.sensors.avro.SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer =
        new SpecificAvroDeserializer<>(
            de.melsicon.kafka.sensors.avro.SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
