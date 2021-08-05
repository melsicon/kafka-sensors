package de.melsicon.kafka.benchmark.serdes;

import com.google.common.flogger.FluentLogger;
import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.SensorStateSerdes;
import java.util.Map;
import javax.inject.Provider;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

@Module
/* package */ abstract class IterationModule {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private IterationModule() {}

  @SuppressWarnings("CloseableProvides")
  @Provides
  /* package */ static Serde<SensorStateWithDuration> serde(
      Map<String, Provider<SensorStateSerdes>> serdes,
      String serDeType,
      Map<String, String> serdeConfig) {
    if (!serdes.containsKey(serDeType)) {
      logger.atWarning().log("Invalid parameter %s", serDeType);
      logger.atInfo().log("Valid parameters are: %s", String.join(", ", serdes.keySet()));
      throw new NullPointerException(String.format("No serde for key %s", serDeType));
    }
    var serde = serdes.get(serDeType).get().createSensorStateWithDurationSerde();
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
