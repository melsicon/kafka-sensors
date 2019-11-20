package de.melsicon.kafka.context;

import com.google.common.util.concurrent.Service;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import de.melsicon.kafka.configuration.InputSerde;
import de.melsicon.kafka.configuration.ResultSerde;
import de.melsicon.kafka.configuration.StoreSerde;
import de.melsicon.kafka.model.SensorState;
import de.melsicon.kafka.model.SensorStateWithDuration;
import de.melsicon.kafka.serde.json.JsonSerdes;
import de.melsicon.kafka.serde.proto.ProtoSerdes;
import de.melsicon.kafka.streams.MyStreams;
import java.util.function.Supplier;
import org.apache.kafka.common.serialization.Serde;

@Module
/* package */ abstract class StreamsModule {
  private StreamsModule() {}

  @Provides
  @InputSerde
  /* package */ static Supplier<Serde<SensorState>> inputSerde(JsonSerdes serdes) {
    return serdes::createSensorStateSerde;
  }

  @Provides
  @StoreSerde
  /* package */ static Supplier<Serde<SensorState>> storeSerde(ProtoSerdes serdes) {
    return serdes::createSensorStateSerde;
  }

  @Provides
  @ResultSerde
  /* package */ static Supplier<Serde<SensorStateWithDuration>> resultSerde(JsonSerdes serdes) {
    return serdes::createSensorStateWithDurationSerde;
  }

  @Binds
  @IntoSet
  /* package */ abstract Service myStream(MyStreams myStreams);
}
