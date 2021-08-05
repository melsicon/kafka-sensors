package de.melsicon.kafka.benchmark.serdes;

import dagger.Module;
import de.melsicon.kafka.serde.avro.AvroModule;
import de.melsicon.kafka.serde.confluent.ConfluentModule;
import de.melsicon.kafka.serde.ion.IonModule;
import de.melsicon.kafka.serde.json.JsonModule;
import de.melsicon.kafka.serde.proto.ProtoModule;

@Module(
    subcomponents = IterationComponent.class,
    includes = {
      AvroModule.class,
      ConfluentModule.class,
      IonModule.class,
      JsonModule.class,
      ProtoModule.class
    })
/* package */ abstract class BenchModule {
  private BenchModule() {}
}
