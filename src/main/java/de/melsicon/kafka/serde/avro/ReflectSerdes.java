package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import javax.inject.Inject;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.reflect.ReflectData;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class ReflectSerdes implements SensorStateSerdes {
  private final AvroMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  public ReflectSerdes(AvroMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "reflect";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var model = ReflectData.get();
    var schema = model.getSchema(SensorState.class);

    var encoder = new BinaryMessageEncoder<SensorState>(model, schema);
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var decoder = new BinaryMessageDecoder<SensorState>(model, schema, RESOLVER);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var model = ReflectData.get();
    var schema = model.getSchema(SensorStateWithDuration.class);

    var encoder = new BinaryMessageEncoder<SensorStateWithDuration>(model, schema);
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var decoder =
        new BinaryMessageDecoder<SensorStateWithDuration>(model, schema, RESOLVER_WITH_DURATION);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
