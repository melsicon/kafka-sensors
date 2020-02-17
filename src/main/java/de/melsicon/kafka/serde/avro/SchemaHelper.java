package de.melsicon.kafka.serde.avro;

import java.util.List;
import org.apache.avro.Schema;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.message.SchemaStore.Cache;

/* package */ final class SchemaHelper {
  /* package */ static final SchemaStore RESOLVER;
  /* package */ static final SchemaStore RESOLVER_WITH_DURATION;

  static {
    RESOLVER = createSchemaStore(allSensorStateSchemata());
    RESOLVER_WITH_DURATION = createSchemaStore(allSensorStateWithDurationSchemata());
  }

  private SchemaHelper() {}

  private static SchemaStore createSchemaStore(Iterable<Schema> schemata) {
    var resolver = new Cache();
    schemata.forEach(resolver::addSchema);
    return resolver;
  }

  private static List<Schema> allSensorStateSchemata() {
    return List.of(
        de.melsicon.kafka.sensors.avro.SensorState.getClassSchema(),
        de.melsicon.kafka.sensors.generic.SensorStateSchema.SCHEMA,
        de.melsicon.kafka.sensors.reflect.SensorState.SCHEMA);
  }

  private static List<Schema> allSensorStateWithDurationSchemata() {
    return List.of(
        de.melsicon.kafka.sensors.avro.SensorStateWithDuration.getClassSchema(),
        de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.SCHEMA,
        de.melsicon.kafka.sensors.reflect.SensorStateWithDuration.SCHEMA);
  }
}
