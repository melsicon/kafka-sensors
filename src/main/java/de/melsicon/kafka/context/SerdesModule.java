package de.melsicon.kafka.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.json.JsonSerdes;
import de.melsicon.kafka.serde.proto.ProtoSerdes;
import org.apache.kafka.common.serialization.Serde;

/** Provides bindings for our (de-)serializers. */
@Module
/* package */ abstract class SerdesModule {
  private SerdesModule() {}

  @SuppressWarnings("CloseableProvides") // closed with JsonSerdes
  @Provides
  @InputSerde
  /* package */ static Serde<SensorState> inputSerde(JsonSerdes serdes) {
    return serdes.createSensorStateSerde();
  }

  @SuppressWarnings("CloseableProvides") // closed with ProtoSerdes
  @Provides
  @StoreSerde
  /* package */ static Serde<SensorState> storeSerde(ProtoSerdes serdes) {
    return serdes.createSensorStateSerde();
  }

  @SuppressWarnings("CloseableProvides") // closed with JsonSerdes
  @Provides
  @ResultSerde
  /* package */ static Serde<SensorStateWithDuration> resultSerde(JsonSerdes serdes) {
    return serdes.createSensorStateWithDurationSerde();
  }
}
