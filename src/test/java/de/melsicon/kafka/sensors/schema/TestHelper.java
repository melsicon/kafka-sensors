package de.melsicon.kafka.sensors.schema;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import org.apache.avro.Schema;

/* package */ final class TestHelper {
  private TestHelper() {}

  /* package */ static ImmutableList<Object[]> createParameters() {
    var sensorStateSchemas =
        List.of(
            de.melsicon.kafka.sensors.type.avro.specific.SchemaHelper.SENSOR_STATE_SCHEMA,
            de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper.SENSOR_STATE_SCHEMA,
            de.melsicon.kafka.sensors.type.avro.reflect.SchemaHelper.SENSOR_STATE_SCHEMA);

    var combinations1 = combinations(sensorStateSchemas);

    var sensorStateWithDurationSchemas =
        List.of(
            de.melsicon.kafka.sensors.type.avro.specific.SchemaHelper
                .SENSOR_STATE_WITH_DURATION_SCHEMA,
            de.melsicon.kafka.sensors.type.avro.generic.SchemaHelper
                .SENSOR_STATE_WITH_DURATION_SCHEMA,
            de.melsicon.kafka.sensors.type.avro.reflect.SchemaHelper
                .SENSOR_STATE_WITH_DURATION_SCHEMA);

    var combinations2 = combinations(sensorStateWithDurationSchemas);

    return ImmutableList.<Object[]>builder().addAll(combinations1).addAll(combinations2).build();
  }

  private static List<Object[]> combinations(Collection<Schema> schemata) {
    return schemata.stream().map(schema -> new Object[] {schema, schemata}).toList();
  }
}
