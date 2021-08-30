package de.melsicon.kafka.sensors.serialization.gson;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import com.google.gson.Gson;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.serialization.mapping.MappedDeserializer;
import de.melsicon.kafka.sensors.serialization.mapping.MappedSerializer;
import de.melsicon.kafka.sensors.type.gson.SensorState;
import de.melsicon.kafka.sensors.type.gson.SensorStateWithDuration;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;

public final class GsonSerdes implements SensorStateSerdes {
  private final Gson gson;
  private final SensorStateMapper<SensorState, SensorStateWithDuration> mapper;

  @Inject
  /* package */ GsonSerdes(
      Gson gson, SensorStateMapper<SensorState, SensorStateWithDuration> mapper) {
    this.gson = gson;
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.GSON_JSON;
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorState> createSensorStateSerde() {
    var adapter = gson.getAdapter(SensorState.class);
    var serializer = new GsonSerializer<>(adapter);
    var deserializer = new GsonDeserializer<>(adapter);

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }

  @Override
  public Serde<de.melsicon.kafka.sensors.model.SensorStateWithDuration>
      createSensorStateWithDurationSerde() {
    var adapter = gson.getAdapter(SensorStateWithDuration.class);
    var serializer = new GsonSerializer<>(adapter);
    var deserializer = new GsonDeserializer<>(adapter);

    var mappedSerializer = new MappedSerializer<>(serializer, mapper::unmap2);
    var mappedDeserializer = new MappedDeserializer<>(deserializer, mapper::map2);

    return serdeFrom(mappedSerializer, mappedDeserializer);
  }
}
