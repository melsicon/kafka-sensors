package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.sensors.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import javax.inject.Inject;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class GenericSerdes implements SensorStateSerdes {
  private final AvroMapper<GenericRecord, GenericRecord> mapper;

  @Inject
  public GenericSerdes(AvroMapper<GenericRecord, GenericRecord> mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "generic";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var model = GenericData.get();
    var schema = SensorStateSchema.SCHEMA;

    var encoder = new BinaryMessageEncoder<GenericRecord>(model, schema);
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var decoder = new BinaryMessageDecoder<GenericRecord>(model, schema, RESOLVER);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var model = GenericData.get();
    var schema = SensorStateWithDurationSchema.SCHEMA;

    var encoder = new BinaryMessageEncoder<GenericRecord>(model, schema);
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var decoder = new BinaryMessageDecoder<GenericRecord>(model, schema, RESOLVER_WITH_DURATION);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
