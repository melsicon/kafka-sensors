package de.melsicon.kafka.sensors.serialization.gson;

import com.google.gson.Gson;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.SensorStateMapper;
import de.melsicon.kafka.sensors.serde.SensorStateSerdes;
import de.melsicon.kafka.sensors.type.gson.GsonHelper;
import de.melsicon.kafka.sensors.type.gson.SensorState;
import de.melsicon.kafka.sensors.type.gson.SensorStateWithDuration;
import javax.inject.Singleton;

@Module
public abstract class GsonModule {
  private GsonModule() {}

  @Provides
  @Singleton
  /* package */ static SensorStateMapper<SensorState, SensorStateWithDuration> jsonMapper() {
    return new GsonMapperImpl();
  }

  @Provides
  @Singleton
  /* package */ static Gson gson() {
    return GsonHelper.gson();
  }

  @Binds
  @IntoMap
  @StringKey(Name.GSON)
  /* package */ abstract SensorStateSerdes gsonSerdes(GsonSerdes serdes);
}
