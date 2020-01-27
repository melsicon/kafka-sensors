package de.melsicon.kafka.serde.confluent;

import de.melsicon.kafka.sensors.reflect.SensorState;
import de.melsicon.kafka.sensors.reflect.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avromapper.ReflectMapper;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroDeserializer;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class ReflectSerdes implements SensorStateSerdes {
  private final ReflectMapper mapper;

  @Inject
  public ReflectSerdes() {
    this.mapper = ReflectMapper.instance();
  }

  @Override
  public String name() {
    return "confluent-reflect";
  }

  @Override
  public Format format() {
    return Format.CONFLUENT;
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorState> createSensorStateSerde() {
    var serializer = new ReflectionAvroSerializer<SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer = new ReflectionAvroDeserializer<>(SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new ReflectionAvroSerializer<SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer = new ReflectionAvroDeserializer<>(SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return Serdes.serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
