package de.melsicon.kafka.schema;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.avro.Schema;

/* package */ final class TestHelper {
  private TestHelper() {}

  /* package */ static ImmutableList<Object[]> createParameters() {
    var sensorStateSchemas =
        List.of(
            de.melsicon.kafka.sensors.avro.SensorState.getClassSchema(),
            de.melsicon.kafka.sensors.reflect.SensorState.SCHEMA,
            de.melsicon.kafka.sensors.generic.SensorStateSchema.SCHEMA);

    var combinations1 = combinations(sensorStateSchemas);

    var sensorStateWithDurationSchemas =
        List.of(
            de.melsicon.kafka.sensors.avro.SensorStateWithDuration.getClassSchema(),
            de.melsicon.kafka.sensors.reflect.SensorStateWithDuration.SCHEMA,
            de.melsicon.kafka.sensors.generic.SensorStateWithDurationSchema.SCHEMA);

    var combinations2 = combinations(sensorStateWithDurationSchemas);

    return ImmutableList.<Object[]>builder().addAll(combinations1).addAll(combinations2).build();
  }

  private static ImmutableList<Object[]> combinations(Iterable<Schema> schemas) {
    var combinations = ImmutableList.<Object[]>builder();

    for (var schema : schemas) {
      var o = new Object[2];
      o[0] = schema;
      o[1] = schemas;
      combinations.add(o);
    }

    return combinations.build();
  }
}
