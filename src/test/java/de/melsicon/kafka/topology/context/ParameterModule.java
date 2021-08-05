package de.melsicon.kafka.topology.context;

import static de.melsicon.kafka.serde.Name.AVRO_SPECIFIC;
import static de.melsicon.kafka.serde.Name.CONFLUENT_SPECIFIC;
import static de.melsicon.kafka.serde.Name.JSON;
import static de.melsicon.kafka.serde.Name.PROTO;

import dagger.Module;
import dagger.Provides;
import de.melsicon.kafka.serde.NamedSerDes;
import de.melsicon.kafka.serde.SensorStateSerdes;
import de.melsicon.kafka.serde.avro.AvroModule;
import de.melsicon.kafka.serde.confluent.ConfluentModule;
import de.melsicon.kafka.serde.json.JsonModule;
import de.melsicon.kafka.serde.proto.ProtoModule;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Provider;

@Module(includes = {AvroModule.class, ConfluentModule.class, JsonModule.class, ProtoModule.class})
/* package */ abstract class ParameterModule {
  private static final String[] SERDES = {PROTO, JSON, AVRO_SPECIFIC, CONFLUENT_SPECIFIC};

  private ParameterModule() {}

  @Provides
  /* package */ static List<NamedSerDes> serdes(Map<String, Provider<SensorStateSerdes>> serdes) {
    Function<String, NamedSerDes> lookup =
        key -> {
          if (!serdes.containsKey(key)) {
            throw new NullPointerException(String.format("Serdes %s missing", key));
          }
          return NamedSerDes.of(key, serdes.get(key).get());
        };
    return Arrays.stream(SERDES).map(lookup).toList();
  }
}
