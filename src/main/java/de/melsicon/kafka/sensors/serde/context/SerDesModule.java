package de.melsicon.kafka.sensors.serde.context;

import dagger.Module;
import de.melsicon.kafka.sensors.serialization.avro.AvroModule;
import de.melsicon.kafka.sensors.serialization.avromapper.AvroMapperModule;
import de.melsicon.kafka.sensors.serialization.confluent.ConfluentModule;
import de.melsicon.kafka.sensors.serialization.confluentmapper.ConfluentMapperModule;
import de.melsicon.kafka.sensors.serialization.ion.IonModule;
import de.melsicon.kafka.sensors.serialization.json.JsonModule;
import de.melsicon.kafka.sensors.serialization.proto.ProtoModule;

@Module(
    includes = {
      AvroModule.class,
      AvroMapperModule.class,
      ConfluentModule.class,
      ConfluentMapperModule.class,
      IonModule.class,
      JsonModule.class,
      ProtoModule.class
    })
public abstract class SerDesModule {
  private SerDesModule() {}
}
