package de.melsicon.kafka.serde.avro;

import org.apache.avro.message.SchemaStore;
import org.apache.avro.message.SchemaStore.Cache;
import org.apache.avro.reflect.ReflectData;

/* package */ final class SchemaHelper {
  /* package */ static final SchemaStore RESOLVER;
  /* package */ static final SchemaStore RESOLVER_WITH_DURATION;

  static {
    RESOLVER = resolver();
    RESOLVER_WITH_DURATION = resolverWithDuration();
  }

  private SchemaHelper() {}

  private static SchemaStore resolver() {
    var resolver = new Cache();
    resolver.addSchema(de.melsicon.kafka.sensors.avro.SensorState.getClassSchema());

    resolver.addSchema(de.melsicon.kafka.sensors.generic.SensorStateSchema.SCHEMA);

    var model = ReflectData.get();
    var schema = model.getSchema(de.melsicon.kafka.sensors.reflect.SensorState.class);
    resolver.addSchema(schema);

    return resolver;
  }

  private static SchemaStore resolverWithDuration() {
    var resolver = new Cache();
    resolver.addSchema(de.melsicon.kafka.sensors.avro.SensorStateWithDuration.getClassSchema());

    resolver.addSchema(de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.SCHEMA);

    var model = ReflectData.get();
    var schema = model.getSchema(de.melsicon.kafka.sensors.reflect.SensorStateWithDuration.class);
    resolver.addSchema(schema);

    return resolver;
  }
}
