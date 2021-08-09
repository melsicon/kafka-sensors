package de.melsicon.kafka.sensors.schema;

import com.google.common.collect.ImmutableList;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateSchema;
import de.melsicon.kafka.sensors.serialization.generic.SensorStateWithDurationSchema;
import de.melsicon.kafka.sensors.serialization.reflect.SensorState;
import de.melsicon.kafka.sensors.serialization.reflect.SensorStateWithDuration;
import java.util.List;
import org.apache.avro.Schema;

/* package */ final class TestHelper {
  private TestHelper() {}

  /* package */ static ImmutableList<Object[]> createParameters() {
    var sensorStateSchemas =
        List.of(
            de.melsicon.kafka.sensors.avro.SensorState.getClassSchema(),
            SensorState.SCHEMA,
            SensorStateSchema.SCHEMA);

    var combinations1 = combinations(sensorStateSchemas);

    var sensorStateWithDurationSchemas =
        List.of(
            de.melsicon.kafka.sensors.avro.SensorStateWithDuration.getClassSchema(),
            SensorStateWithDuration.SCHEMA,
            SensorStateWithDurationSchema.SCHEMA);

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
