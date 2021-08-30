package de.melsicon.kafka.sensors.topology.context;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.sensors.serde.Name;
import de.melsicon.kafka.sensors.serde.NamedSerDes;
import de.melsicon.kafka.sensors.serde.SerDesStore;
import de.melsicon.kafka.sensors.serde.context.SerDesModule;
import java.util.Arrays;
import java.util.List;

@Module(includes = SerDesModule.class)
/* package */ abstract class ParameterModule {
  private static final String[] SERDES = {
    Name.PROTO, Name.JSON, Name.AVRO_DIRECT, Name.CONFLUENT_DIRECT
  };

  private ParameterModule() {}

  @Provides
  /* package */ static List<NamedSerDes> serdes(SerDesStore store) {
    return Arrays.stream(SERDES).map(key -> NamedSerDes.of(key, store.serdes(key))).toList();
  }
}
