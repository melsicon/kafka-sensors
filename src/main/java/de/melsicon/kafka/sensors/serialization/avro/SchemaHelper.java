package de.melsicon.kafka.sensors.serialization.avro;

import de.melsicon.kafka.sensors.avro.SensorState;
import de.melsicon.kafka.sensors.avro.SensorStateWithDuration;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateWithDurationSchema;
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
        SensorState.getClassSchema(),
        SensorStateSchema.SCHEMA,
        de.melsicon.kafka.sensors.serialization.reflect.SensorState.SCHEMA);
  }

  private static List<Schema> allSensorStateWithDurationSchemata() {
    return List.of(
        SensorStateWithDuration.getClassSchema(),
        SensorStateWithDurationSchema.SCHEMA,
        de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration.SCHEMA);
  }
}
