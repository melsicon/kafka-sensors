package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;

public final class JsonHelper {
  private JsonHelper() {}

  public static ObjectMapper mapper() {
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
    return mapper;
  }
}
