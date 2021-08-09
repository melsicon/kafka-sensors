package de.melsicon.kafka.sensors.benchmark.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serde.SerDesStore;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

@Module
/* package */ abstract class IterationModule {
  private IterationModule() {}

  @SuppressWarnings("CloseableProvides")
  @Provides
  /* package */ static Serde<SensorStateWithDuration> serde(
      SerDesStore store, String serDeType, Map<String, String> serdeConfig) {
    var serde = store.serdes(serDeType).createSensorStateWithDurationSerde();
    serde.configure(serdeConfig, /* isKey= */ false);
    return serde;
  }

  @SuppressWarnings("CloseableProvides")
  @Provides
  /* package */ static Serializer<SensorStateWithDuration> serializer(
      Serde<SensorStateWithDuration> serde) {
    return serde.serializer();
  }

  @SuppressWarnings("CloseableProvides")
  @Provides
  /* package */ static Deserializer<SensorStateWithDuration> deserializer(
      Serde<SensorStateWithDuration> serde) {
    return serde.deserializer();
  }
}
