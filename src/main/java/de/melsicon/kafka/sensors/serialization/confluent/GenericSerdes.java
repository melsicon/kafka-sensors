package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;

public final class GenericSerdes implements SensorStateSerdes {
  private final SensorStateMapper<GenericRecord, GenericRecord> mapper;

  @Inject
  /* package */ GenericSerdes(
      @Named("confluent") SensorStateMapper<GenericRecord, GenericRecord> mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_AVRO;
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
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer = new GenericAvroSerializer();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new GenericAvroDeserializer(SensorStateWithDurationSchema.SCHEMA);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
