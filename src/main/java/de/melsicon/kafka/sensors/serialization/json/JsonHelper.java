package de.melsicon.kafka.sensors.serialization.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.melsicon.kafka.sensors.model.ImmutableSensorState;
import de.melsicon.kafka.sensors.model.ImmutableSensorStateWithDuration;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;

public final class JsonHelper {
  private JsonHelper() {}

  public static ObjectMapper mapper() {
    return JsonMapper.builder()
        .addModules(new Jdk8Module(), new JavaTimeModule(), new GuavaModule())
        .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, /* state= */ true)
        .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, /* state= */ true)
        .addMixIn(SensorState.class, SensorStateMixIn.class)
        .addMixIn(ImmutableSensorState.Builder.class, SensorStateMixIn.BuilderMixIn.class)
        .addMixIn(SensorStateWithDuration.class, SensorStateWithDurationMixIn.class)
        .addMixIn(
            ImmutableSensorStateWithDuration.Builder.class,
            SensorStateWithDurationMixIn.BuilderMixIn.class)
        .build();
  }
}
