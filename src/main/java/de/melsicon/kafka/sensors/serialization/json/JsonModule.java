package de.melsicon.kafka.sensors.serialization.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.type.json.JsonHelper;
import de.melsicon.kafka.sensors.type.json.SensorState;
import de.melsicon.kafka.sensors.type.json.SensorStateWithDuration;
import de.melsicon.kafka.sensors.type.mixin.MixInHelper;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public abstract class JsonModule {
  private JsonModule() {}

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<SensorState, SensorStateWithDuration> jsonMapper() {
    return new JsonMapperImpl();
  }

  @Provides
  @Singleton
  /* package */ static ObjectMapper mapper() {
    return JsonHelper.mapper();
  }

  @Provides
  @Singleton
  @Named("mixin")
  /* package */ static ObjectMapper mixInMapper() {
    return MixInHelper.mapper();
  }

  @Binds
  @IntoMap
  @StringKey(Name.JSON)
  /* package */ abstract SensorStateSerdes jsonSerdes(JsonSerdes serdes);

  @Binds
  @IntoMap
  @StringKey(Name.MIXIN)
  /* package */ abstract SensorStateSerdes mixInSerdes(MixinSerdes serdes);
}
