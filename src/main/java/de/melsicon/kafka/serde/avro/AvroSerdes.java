package de.melsicon.kafka.serde.avro;

import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER;
import static de.melsicon.kafka.serde.avro.SchemaHelper.RESOLVER_WITH_DURATION;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.AvroMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class AvroSerdes implements SensorStateSerdes {
  private final AvroMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  public AvroSerdes(AvroMapper<SensorState, SensorStateWithDuration> mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "avro";
  }

  @Override
  public Format format() {
    return Format.AVRO;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var encoder = SensorState.getEncoder();
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var decoder = SensorState.createDecoder(RESOLVER);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var encoder = SensorStateWithDuration.getEncoder();
    var serializer = new AvroSerializer<>(encoder);
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var decoder = SensorStateWithDuration.createDecoder(RESOLVER_WITH_DURATION);
    var deserializer = new AvroDeserializer<>(decoder);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
