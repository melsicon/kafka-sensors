package de.melsicon.kafka.serde.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;

public final class JsonHelper {
  private JsonHelper() {}

  public static ObjectMapper mapper() {
    return JsonMapper.builder()
        .addModules(new Jdk8Module(), new JavaTimeModule(), new GuavaModule())
        .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, /* state= */ true)
        .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, /* state= */ true)
        .addMixIn(SensorState.class, SensorStateMixIn.class)
        .addMixIn(SensorState.Builder.class, SensorStateMixIn.BuilderMixIn.class)
        .addMixIn(SensorStateWithDuration.class, SensorStateWithDurationMixIn.class)
        .addMixIn(
            SensorStateWithDuration.Builder.class, SensorStateWithDurationMixIn.BuilderMixIn.class)
        .build();
  }
}
