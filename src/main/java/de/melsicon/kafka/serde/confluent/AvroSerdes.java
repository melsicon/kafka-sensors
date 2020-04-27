package de.melsicon.kafka.serde.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class AvroSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.avro.SensorState,
          de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
      mapper;

  @Inject
  public AvroSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.avro.SensorState,
              de.melsicon.kafka.sensors.avro.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "confluent";
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
