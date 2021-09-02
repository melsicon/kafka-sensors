package de.melsicon.kafka.sensors.type.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.Duration;
import java.time.Instant;

public final class GsonHelper {
  private GsonHelper() {}

  public static Gson gson() {
    var gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Instant.class, new InstantTypeConverter());
    gsonBuilder.registerTypeAdapter(Duration.class, new DurationTypeConverter());
    gsonBuilder.registerTypeAdapter(SensorStateBase.State.class, new StateTypeConverter());
    gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersGson());
    return gsonBuilder.create();
  }
}
