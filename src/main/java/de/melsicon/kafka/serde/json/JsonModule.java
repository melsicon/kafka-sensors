package de.melsicon.kafka.serde.json;

import static de.melsicon.kafka.serde.Name.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.serde.SensorStateSerdes;
import javax.inject.Singleton;

@Module
public abstract class JsonModule {
  private JsonModule() {}

  @Binds
  @IntoMap
  @StringKey(JSON)
  /* package */ abstract SensorStateSerdes jsonSerdes(JsonSerdes serdes);

  @Provides
  @Singleton
  /* package */ static ObjectMapper objectMapper() {
    return JsonHelper.mapper();
  }
}
