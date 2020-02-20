package de.melsicon.kafka.serde.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.sensors.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.avromapper.ConfluentGenericMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer;
import javax.inject.Inject;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;

public final class GenericSerdes implements SensorStateSerdes {
  private final AvroMapper<GenericRecord, GenericRecord> mapper;

  @Inject
  public GenericSerdes(ConfluentGenericMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "confluent-generic";
  }

  @Override
  public Format format() {
    return Format.CONFLUENT;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new GenericAvroSerializer();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new GenericAvroDeserializer(SensorStateSchema.SCHEMA);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new GenericAvroSerializer();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new GenericAvroDeserializer(SensorStateWithDurationSchema.SCHEMA);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
