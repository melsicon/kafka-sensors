package de.melsicon.kafka.sensors.serialization.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class JsonSerdes implements SensorStateSerdes {
  private final SensorStateMapper<
          de.melsicon.kafka.sensors.serialization.confluent_json.SensorState,
          de.melsicon.kafka.sensors.serialization.confluent_json.SensorStateWithDuration>
      mapper;

  @Inject
  /* package */ JsonSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.serialization.confluent_json.SensorState,
              de.melsicon.kafka.sensors.serialization.confluent_json.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_JSON;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer =
        new KafkaJsonSchemaSerializer<
            de.melsicon.kafka.sensors.serialization.confluent_json.SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer =
        new KafkaJsonSchemaDeserializer<>(
            de.melsicon.kafka.sensors.serialization.confluent_json.SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new KafkaJsonSchemaSerializer<
            de.melsicon.kafka.sensors.serialization.confluent_json.SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer =
        new KafkaJsonSchemaDeserializer<>(
            de.melsicon.kafka.sensors.serialization.confluent_json.SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
