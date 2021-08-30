package de.melsicon.kafka.sensors.serialization.json;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.json.SensorState;
import de.melsicon.kafka.sensors.type.json.SensorStateWithDuration;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class JsonSerdes implements SensorStateSerdes {
  private final ObjectMapper objectMapper;
  private final SensorStateMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  /* package */ JsonSerdes(
      ObjectMapper objectMapper, SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    this.objectMapper = objectMapper;
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.JSON;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var serializer = new JsonSerializer<>(objectMapper, SensorState.class);
    var deserializer = new JsonDeserializer<>(objectMapper, SensorState.class);

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var serializer = new JsonSerializer<>(objectMapper, SensorStateWithDuration.class);
    var deserializer = new JsonDeserializer<>(objectMapper, SensorStateWithDuration.class);

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
