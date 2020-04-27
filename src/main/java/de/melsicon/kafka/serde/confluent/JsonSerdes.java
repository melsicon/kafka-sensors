package de.melsicon.kafka.serde.confluent;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.Format;
import de.melsicon.kafka.serde.SensorStateMapper;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.mapping.MappedDeserializer;
import de.melsicon.kafka.serde.mapping.MappedSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class JsonSerdes implements SensorStateSerdes {
  SensorStateMapper<
          de.melsicon.kafka.sensors.confluent_json.SensorState,
          de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration>
      mapper;

  @Inject
  public JsonSerdes(
      SensorStateMapper<
              de.melsicon.kafka.sensors.confluent_json.SensorState,
              de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration>
          mapper) {
    this.mapper = mapper;
  }

  @Override
  public String name() {
    return "confluent-json";
  }

  @Override
  public Format format() {
    return Format.CONFLUENT_JSON;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer =
        new KafkaJsonSchemaSerializer<de.melsicon.kafka.sensors.confluent_json.SensorState>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);

    var deserializer =
        new KafkaJsonSchemaDeserializer<>(
            de.melsicon.kafka.sensors.confluent_json.SensorState.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer =
        new KafkaJsonSchemaSerializer<
            de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration>();
    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);

    var deserializer =
        new KafkaJsonSchemaDeserializer<>(
            de.melsicon.kafka.sensors.confluent_json.SensorStateWithDuration.class);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
