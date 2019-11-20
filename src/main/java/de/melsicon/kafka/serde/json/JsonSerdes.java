package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import javax.inject.Inject;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class JsonSerdes implements SensorStateSerdes {
  private static final ObjectMapper MAPPER;

  static {
    var mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new GuavaModule());
    mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, /* state= */ true);
    mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, /* state= */ true);
    mapper.addMixIn(SensorState.class, SensorStateMixIn.class);
    mapper.addMixIn(SensorState.Builder.class, SensorStateMixIn.BuilderMixIn.class);
    mapper.addMixIn(SensorStateWithDuration.class, SensorStateWithDurationMixIn.class);
    mapper.addMixIn(
        SensorStateWithDuration.Builder.class, SensorStateWithDurationMixIn.BuilderMixIn.class);
    MAPPER = mapper;
  }

  @Inject
  public JsonSerdes() {}

  @Override
  public String name() {
    return "json";
  }

  @Override
  public Serde<SensorState> createSensorStateSerde() {
    var serializer = new JsonSerializer<>(MAPPER, SensorState.class);
    var deserializer = new JsonDeserializer<>(MAPPER, SensorState.class);

    return Serdes.serdeFrom(serializer, deserializer);
  }

  @Override
  public Serde<SensorStateWithDuration> createSensorStateWithDurationSerde() {
    var serializer = new JsonSerializer<>(MAPPER, SensorStateWithDuration.class);
    var deserializer = new JsonDeserializer<>(MAPPER, SensorStateWithDuration.class);

    return Serdes.serdeFrom(serializer, deserializer);
  }
}
