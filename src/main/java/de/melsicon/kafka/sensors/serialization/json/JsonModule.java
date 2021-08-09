package de.melsicon.kafka.sensors.serialization.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import javax.inject.Singleton;

@Module
public abstract class JsonModule {
  private JsonModule() {}

  @Provides
  @Singleton
  /* package */ static ObjectMapper objectMapper() {
    return JsonHelper.mapper();
  }

  @Binds
  @IntoMap
  @StringKey(Name.JSON)
  /* package */ abstract SensorStateSerdes jsonSerdes(JsonSerdes serdes);
}
