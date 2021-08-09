package de.melsicon.kafka.sensors.serde.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.sensors.configuration.InputSerde;
import de.melsicon.kafka.sensors.configuration.ResultSerde;
import de.melsicon.kafka.sensors.configuration.StoreSerde;
import de.melsicon.kafka.sensors.model.SensorState;
import de.melsicon.kafka.sensors.model.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serialization.json.JsonModule;
import de.melsicon.kafka.sensors.serialization.json.JsonSerdes;
import de.melsicon.kafka.sensors.serialization.proto.ProtoModule;
import de.melsicon.kafka.sensors.serialization.proto.ProtoSerdes;
import org.apache.kafka.common.serialization.Serde;

@Module(includes = {JsonModule.class, ProtoModule.class})
public abstract class AppSerDesModule {
  private AppSerDesModule() {}

  @SuppressWarnings("CloseableProvides")
  @Provides
  @InputSerde
  /* package */ static Serde<SensorState> inputSerde(JsonSerdes serdes) {
    return serdes.createSensorStateSerde();
  }

  @SuppressWarnings("CloseableProvides")
  @Provides
  @StoreSerde
  /* package */ static Serde<SensorState> storeSerde(ProtoSerdes serdes) {
    return serdes.createSensorStateSerde();
  }

  @SuppressWarnings("CloseableProvides")
  @Provides
  @ResultSerde
  /* package */ static Serde<SensorStateWithDuration> resultSerde(JsonSerdes serdes) {
    return serdes.createSensorStateWithDurationSerde();
  }
}
