package de.melsicon.kafka.serde.confluent;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class AvroSerdes implements SensorStateSerdes {
  private final AvroMapper mapper;

  @Inject
  public AvroSerdes() {
    this.mapper = AvroMapper.instance();
  }

  @Override
  public String name() {
    return "confluent";
  }

  @Override
  public Format format() {
    return Format.CONFLUENT;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var serializer = new SpecificAvroSerializer<SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new SpecificAvroDeserializer<>(SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new SpecificAvroSerializer<SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new SpecificAvroDeserializer<>(SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
