package de.melsicon.kafka.sensors.serialization.json;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.Format;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.type.json.JsonDeserializer;
import de.melsicon.kafka.sensors.type.json.JsonSerializer;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.kafka.common.serialization.Serde;

public final class MixinSerdes implements SensorStateSerdes {
  private final ObjectMapper mapper;

  @Inject
  /* package */ MixinSerdes(@Named("mixin") ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public Format format() {
    return Format.JSON;
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new JsonSerializer<>(mapper, SensorState.class);
    var deserializer = new JsonDeserializer<>(mapper, SensorState.class);

    return serdeFrom(serializer, deserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer = new JsonSerializer<>(mapper, SensorStateWithDuration.class);
    var deserializer = new JsonDeserializer<>(mapper, SensorStateWithDuration.class);

    return serdeFrom(serializer, deserializer);
  }
}
