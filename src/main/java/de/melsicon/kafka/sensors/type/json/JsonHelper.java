package de.melsicon.kafka.sensors.type.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonHelper {
  private JsonHelper() {}

  public static ObjectMapper mapper() {
    return JsonMapper.builder()
        .addModules(new Jdk8Module(), new JavaTimeModule(), new GuavaModule())
        .build();
  }
}
